package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemService itemService;
//    private final UserService userService;

    @Override   //todo****************
    public BookingDtoResponse creatBooking(BookingDto bookingDto, Long userId) throws NotFoundException {
        Booking booking = BookingMapper.toBooking(bookingDto);
        Item item = itemService.getItem(bookingDto.getItemId());
        bookingRepository.save(booking);
        return BookingMapper.bookingDtoResponse(booking);
    }

    @Override   //todo****************
    public BookingDtoResponse updateBooking(Long bookingId, Long userId, Boolean approved) {
        return null;
    }

    @Override   //todo****************
    public BookingDtoResponse getById(Long bookingId, Long userId) {
        return null;
    }

    @Override   //todo****************
    public List<BookingDtoResponse> getAll(Long userId, String state) {
        return null;
    }

    @Override   //todo****************
    public List<BookingDtoResponse> getOwnerItemsAll(Long userId, String state) {
        return null; //todo
    }
}
