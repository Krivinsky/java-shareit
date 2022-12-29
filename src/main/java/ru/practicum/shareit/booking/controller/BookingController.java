package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exeption.UnsupportedState;

import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {


    private final BookingService bookingService;

    @PostMapping
    public BookingDtoResponse creatBooking(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                           @RequestBody BookingDtoRequest bookingDtoRequest) {
        BookingDtoResponse result;
        Booking booking = BookingMapper.toBooking(bookingDtoRequest);
        return  result = BookingMapper.toDtoResponse(bookingService.creatBooking(booking));
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse updateBookingStatus (@RequestHeader ("X-Sharer-User-Id") Long userId,
                                                   @PathVariable Long bookingId,
                                                   @RequestParam("approver") Boolean approved) {
        return bookingService.updateBooking(bookingService.getBooking(bookingId, userId, approved));
    }


    @GetMapping("/{bookingId}")
    public BookingDtoResponse getBooking (@RequestHeader ("X-Sharer-User-Id") Long userId,
                                           @PathVariable Long bookingId) {
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping
    public List<BookingDtoResponse> getAll(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                           @RequestParam (value = "state", required = false) String state) throws UnsupportedState {
        List<BookingDtoResponse> result = null;
        bookingService.getAll(userId, state);
        return result;
    }

    @GetMapping("/owner")
    public List<BookingDtoResponse> getOwnerItemsAll(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                                     @RequestParam (value = "state", required = false) {
        return null;
    }
}
