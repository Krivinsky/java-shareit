package ru.practicum.shareit.booking.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.exeption.ItemException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UnsupportedState;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;
    private final Sort sort = Sort.by(Sort.Direction.DESC, "start");

    public BookingServiceImpl(BookingRepository bookingRepository,
                              ItemRepository itemRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookingDtoResponse creatBooking(BookingDto bookingDto, Long userId) throws NotFoundException, ItemException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("не найден пользователь с id " + userId));

        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() ->
                new NotFoundException("не найдена вещь с id " + bookingDto.getItemId()));

        if (item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("пользователь не может забронировать принадлежащую ему вещь");
        }
        if (!item.getAvailable()) {
            throw new ItemException("данная вещь недоступна");
        }
        Booking booking = BookingMapper.toBooking(bookingDto);
        if (booking.getEnd().isBefore(booking.getStart())) {
            throw new ItemException("дата окончания бронирования не может быть раньше даты начала бронирования");
        }
        booking.setBooker(user);
        booking.setItem(item);
        booking.setStatus(Status.WAITING);
        bookingRepository.save(booking);

        return BookingMapper.toBookingDtoResponse(booking);
    }

    @Override
    public BookingDtoResponse updateBooking(Long bookingId, Long userId, Boolean approved) throws NotFoundException, ItemException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException("Невозможно подтвердить бронирование - " +
                        "не найдено бронирование с id " + bookingId));
        if (!userId.equals(booking.getItem().getOwner().getId())) {
            throw new NotFoundException("Невозможно подтвердить бронирование - " +
                    "не найдено бронирование с id " + bookingId + " у пользователя с id" + userId);
        }
        if (!booking.getStatus().equals(Status.WAITING)) {
            throw new ItemException("бронирование уже подтверждено или отклонено");
        }
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        bookingRepository.save(booking);
        return BookingMapper.toBookingDtoResponse(booking);
    }

    @Override
    public BookingDtoResponse getBooking(Long bookingId, Long userId) throws NotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            throw new NotFoundException("Такого бронирования не существует");
        }
        Booking booking = bookingOptional.get();
        if (!Objects.equals(booking.getBooker().getId(), userId) && !Objects.equals(booking.getItem().getOwner().getId(), userId)) {
            throw new NotFoundException("Пользователь не является владельцем вещи или автором бронирования");
        }
        return BookingMapper.toBookingDtoResponse(booking);
    }

    @Override
    public List<BookingDtoResponse> getAll(Long userId, String state, Long from, Long size) throws UnsupportedState {
        PageRequest pageRequest = PageRequest.of(from.intValue() / size.intValue(), size.intValue(), sort);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("не найден пользователь с id " + userId));
        List<Booking> bookingDtoList = new ArrayList<>();
        switch (state) {
            case "ALL":
                bookingDtoList.addAll(bookingRepository.findAllByBooker(user, pageRequest));
                break;
            case "CURRENT":
                bookingDtoList.addAll(bookingRepository.findAllByBookerAndStartBeforeAndEndAfter(user,
                        LocalDateTime.now(), LocalDateTime.now(), pageRequest));
                break;
            case "PAST":
                bookingDtoList.addAll(bookingRepository.findAllByBookerAndEndBefore(user,
                        LocalDateTime.now(), pageRequest));
                break;
            case "FUTURE":
                bookingDtoList.addAll(bookingRepository
                        .findAllByBookerAndStartAfter(user, LocalDateTime.now(), pageRequest));
                break;
            case "WAITING":
                bookingDtoList.addAll(bookingRepository
                        .findAllByBookerAndStatusEquals(user, Status.WAITING, pageRequest));
                break;
            case "REJECTED":
                bookingDtoList.addAll(bookingRepository
                        .findAllByBookerAndStatusEquals(user, Status.REJECTED, pageRequest));
                break;
            default:
                throw new UnsupportedState("Unknown state: UNSUPPORTED_STATUS");
        }

        return bookingDtoList.stream().map(BookingMapper::toBookingDtoResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingDtoResponse> getOwnerItemsAll(Long userId, String state, Long from, Long size)
            throws NotFoundException, UnsupportedState {
        PageRequest pageRequest = PageRequest.of(from.intValue() / size.intValue(), size.intValue(), sort);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("не существует пользователя с id " + userId));
        List<Booking> bookingDtoList = new ArrayList<>();
        switch (state) {
            case "ALL":
                bookingDtoList.addAll(bookingRepository.findAllByItemOwner(user,pageRequest));
                break;
            case "CURRENT":
                bookingDtoList.addAll(bookingRepository.findAllByItemOwnerAndStartBeforeAndEndAfter(user,
                        LocalDateTime.now(), LocalDateTime.now(), pageRequest));
                break;
            case "PAST":
                bookingDtoList.addAll(bookingRepository.findAllByItemOwnerAndEndBefore(user,
                        LocalDateTime.now(), pageRequest));
                break;
            case "FUTURE":
                bookingDtoList.addAll(bookingRepository
                        .findAllByItemOwnerAndStartAfter(user, LocalDateTime.now(), pageRequest));
                break;
            case "WAITING":
                bookingDtoList.addAll(bookingRepository
                        .findAllByItemOwnerAndStatusEquals(user, Status.WAITING, pageRequest));
                break;
            case "REJECTED":
                bookingDtoList.addAll(bookingRepository
                        .findAllByItemOwnerAndStatusEquals(user, Status.REJECTED, pageRequest));
                break;
            default:
                throw new UnsupportedState("Unknown state: UNSUPPORTED_STATUS");
        }
        return bookingDtoList.stream().map(BookingMapper::toBookingDtoResponse).collect(Collectors.toList());
    }
}
