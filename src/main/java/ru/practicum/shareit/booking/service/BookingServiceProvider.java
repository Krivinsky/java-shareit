package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.BookingState;
import ru.practicum.shareit.Status;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingEntity;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.StorageException;
import ru.practicum.shareit.exeption.UnsupportedState;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserEntity;
import ru.practicum.shareit.user.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookingServiceProvider implements BookingService {

    ItemService itemService;
    UserService userService;
    BookingRepository repository;

    public Booking creatBooking(Booking booking) throws StorageException {
        Item item = itemService.getById(booking.getItem().getId(), booking.getBooker().getId());
        User booker = userService.getById(booking.getBooker().getId());
        validate(booking, item);

        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(Status.WAITING);
        try {
            return mapper.toBooking(repository.save(mapper.toEntity(booking)));
        } catch (Exception exception) {
            throw new StorageException("ошибка");
        }
    }

    @Override
    public Booking approve(Long bookingId, Long userId, Boolean approved) throws NotFoundException, StorageException, ValidationException {
        BookingEntity stored = repository.findById(bookingId)
                .orElseThow(NotFoundException::new);
        if (!Objects.equals(userId, stored.getItem().getOwner().getId())) {
            throw new NotFoundException("не найдено");
        }
        if (!Objects.equals(Status.WAITING, stored.getStatus())) {
            throw new ValidationException("Ошибка валидации");
        }
        stored.setStatus((approved ? Status.APPROVED : Status.REJECTED));
        try {
            return mapper.toBooking(repository.save(stored));
        } catch (Exception exception) {
            throw new StorageException("ошибка");
        }
    }

    @Override
    public Booking getById(Long bookingId, Long userId) throws NotFoundException {
        Booking booking = repository.findById(bookingId)
                .map(mapper::toBooking)
                .orElseThrow(NotFoundException::new);
        if (Objects.equals(userId, booking.getItem().getOwner().getId())
            || Objects.equals(userId, booking.getBooker().getId())) {
                return booking;
            }
            throw new NotFoundException("не найдено");
    }

    @Override
    public List<Booking> getAll(Long userId, String state) throws UnsupportedState {
        List<BookingEntity> result;
        UserEntity booker = userRepositoryMapper.toEntity(userService.getById(userId));
        BookingState bookingState = Objects.isNull(state) ? BookingState.ALL : BookingState.of(state);
        switch (bookingState) {
            case ALL:
                result = repository.findAllByBookerOrderByStartDesc(booker);
                break;
            case CURRENT:
                result = repository.findCurrentByBooker(booker, Timestamp.valueOf(LocalDateTime.now()));
                break;
            case PAST:
                result = repository.findPastByBooker(booker, Timestamp.valueOf(LocalDateTime.now()));
                break;
            case FUTURE:
                result = repository.findFutureByBooker(booker, Timestamp.valueOf(LocalDateTime.now()));
                break;
            case WAITING:
                result = repository.findAllByBookerAndStatusOrderByStartDesc(booker, Status.WAITING);
                break;
            case UNKNOWN:
            default:
                throw new UnsupportedState();
        }
        return result.stream()
                .map(mapper::toBooking)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getOwnerItemsAll(Long userId, String state) {

    }

    private void validate(Booking booking, Item item) {

    }
}
