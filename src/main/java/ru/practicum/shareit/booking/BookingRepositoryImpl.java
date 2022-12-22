package ru.practicum.shareit.booking;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookingRepositoryImpl implements  BookingRepository {

    private final Map<Long, Booking> bookings = new HashMap<>();
    @Override
    public Booking creatBooking(Booking booking, Long userId) {
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
}
