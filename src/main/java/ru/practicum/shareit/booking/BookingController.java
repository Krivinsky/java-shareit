package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exeption.NotFoundException;

import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDtoResponse creatBooking(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                           @RequestBody BookingDto bookingDto) throws NotFoundException {

        return bookingService.creatBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse updateBookingStatus(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                   @PathVariable Long bookingId,
                                                   @RequestParam("approver") Boolean approved) {
        return bookingService.updateBooking(bookingId, userId, approved);
    }


    @GetMapping("/{bookingId}")
    public BookingDtoResponse getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @PathVariable Long bookingId) throws NotFoundException {
        return bookingService.getById(bookingId, userId);
    }

    @GetMapping
    public List<BookingDtoResponse> getAll(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                           @RequestParam (value = "state", required = false) String state) {

        return bookingService.getAll(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDtoResponse> getOwnerItemsAll(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                                     @RequestParam (value = "state", required = false) String state) {
        return bookingService.getOwnerItemsAll(userId, state);
    }
}
