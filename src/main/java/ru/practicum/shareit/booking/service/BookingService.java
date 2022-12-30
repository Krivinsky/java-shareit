package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.StorageException;
import ru.practicum.shareit.exeption.UnsupportedState;
import ru.practicum.shareit.exeption.ValidationException;

import java.util.List;

public interface BookingService {

    BookingDtoResponse creatBooking(BookingDto bookingDto, Long userId) throws StorageException, NotFoundException;

    BookingDtoResponse updateBooking(Long bookingId, Long userId, Boolean approved) throws NotFoundException, StorageException, ValidationException;

    BookingDtoResponse getById(Long bookingId, Long userId) throws NotFoundException;

    List<BookingDtoResponse> getAll(Long userId, String state) throws UnsupportedState;

    List<BookingDtoResponse> getOwnerItemsAll(Long userId, String state);
}
