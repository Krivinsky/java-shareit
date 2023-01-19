package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.exeption.ItemException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UnsupportedState;

import java.util.List;

public interface BookingService {

    BookingDtoResponse creatBooking(BookingDto bookingDto, Long userId) throws NotFoundException, ItemException;

    BookingDtoResponse updateBooking(Long bookingId, Long userId, Boolean approved) throws NotFoundException, ItemException;

    BookingDtoResponse getBooking(Long bookingId, Long userId) throws NotFoundException;

    List<BookingDtoResponse> getAll(Long userId, String state, Long from, Long size) throws UnsupportedState;

    List<BookingDtoResponse> getOwnerItemsAll(Long userId, String state, Long from, Long size) throws NotFoundException, UnsupportedState;
}
