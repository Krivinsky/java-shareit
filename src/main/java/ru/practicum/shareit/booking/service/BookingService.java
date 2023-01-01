package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.exeption.NotFoundException;

import java.util.List;

public interface BookingService {

    BookingDtoResponse creatBooking(BookingDto bookingDto, Long userId) throws NotFoundException;

    BookingDtoResponse updateBooking(Long bookingId, Long userId, Boolean approved);

    BookingDtoResponse getById(Long bookingId, Long userId);

    List<BookingDtoResponse> getAll(Long userId, String state);

    List<BookingDtoResponse> getOwnerItemsAll(Long userId, String state);
}
