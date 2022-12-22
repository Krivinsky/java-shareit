package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemService;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Booking creatBooking(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                @RequestBody Booking booking) {
        return booking = bookingService.creatBooking(booking, userId);
    }


}
