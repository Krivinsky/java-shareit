package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {
    Booking creatBooking(Booking booking, Long userId);

    Booking updateBooking(Booking booking);

    List<Booking> getAll(Long userId);

    Booking getBooking();
}
