package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepositiry;
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
