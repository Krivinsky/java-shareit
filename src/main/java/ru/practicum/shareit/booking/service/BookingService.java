package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.StorageException;
import ru.practicum.shareit.exeption.UnsupportedState;
import ru.practicum.shareit.exeption.ValidationException;

import java.util.List;

public interface BookingService {

    Booking creatBooking(Booking booking) throws StorageException;

    Booking approve(Long bookingId, Long userId, Boolean approved) throws NotFoundException, StorageException, ValidationException;

    //Booking updateBooking(Booking booking);

    Booking getById(Long bookingId, Long userId) throws NotFoundException;

    List<Booking> getAll(Long userId, String state) throws UnsupportedState;

    List<Booking> getOwnerItemsAll(Long userId, String state);
}
