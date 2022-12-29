package ru.practicum.shareit.booking.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.Booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookingRepositoryImpl implements  BookingRepository {

    private final Map<Long, Booking> bookings = new HashMap<>();

    private static Long generatedId = 0L;

    private static Long generateId() {
        return ++generatedId;
    }
    @Override
    public Booking creatBooking(Booking booking, Long userId) {
        validata(booking);
        bookings.put(booking.id, booking);
        return null;
    }


    @Override
    public Booking updateBooking(Booking booking) {
        return null;
    }

    @Override
    public List<Booking> getAll(Long userId) {
        return null;
    }

    @Override
    public Booking getBooking() {
        return null;
    }

    private void validata(Booking booking) {
    }
}
