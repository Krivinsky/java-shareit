package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exeption.ErrorResponse;
import ru.practicum.shareit.exeption.ItemException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UnsupportedState;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@Validated
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDtoResponse creatBooking(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                           @Valid  @RequestBody BookingDto bookingDto) throws NotFoundException, ItemException {
        BookingDtoResponse bookingDtoResponse = bookingService.creatBooking(bookingDto, userId);
        log.info("Создано бронирование пользователя - " + userId);
        return bookingDtoResponse;
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse updateBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                            @PathVariable Long bookingId,
                                            @RequestParam("approved") Boolean approved) throws ItemException, NotFoundException {
        log.info("Обновлено бронирование пользователя - " + userId);
        return bookingService.updateBooking(bookingId, userId, approved);
    }


    @GetMapping("/{bookingId}")
    public BookingDtoResponse getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @PathVariable Long bookingId) throws NotFoundException {
        BookingDtoResponse bookingDtoResponse = bookingService.getBooking(bookingId, userId);
        log.info("Получено бронирование " + bookingId + " пользователем - " + userId);
        return  bookingDtoResponse;
    }

    @GetMapping
    public List<BookingDtoResponse> getAll(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                           @RequestParam (value = "state", required = false, defaultValue =  "ALL") String state,
                                           @Min (0) @RequestParam (value = "from", required = false, defaultValue = "0") Long from,
                                           @Min (1) @RequestParam (value = "size", required = false, defaultValue = "10") Long size) throws UnsupportedState {
        List<BookingDtoResponse> bookingDtoResponse = bookingService.getAll(userId, state, from, size);
        log.info("получен список из - " + bookingDtoResponse.size() + " бронирований");
        return bookingDtoResponse;
    }

    @GetMapping("/owner")
    public List<BookingDtoResponse> getOwnerItemsAll(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                                     @RequestParam (value = "state", required = false, defaultValue =  "ALL") String state,
                                                     @Min (0) @RequestParam (value = "from", required = false, defaultValue = "0") Long from,
                                                     @Min (1) @RequestParam (value = "size", required = false, defaultValue = "10") Long size) throws UnsupportedState, NotFoundException {
        log.info("Получен список бронирований");
        return bookingService.getOwnerItemsAll(userId, state, from, size);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIncorrectParameter(NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectParameterException(UnsupportedState e) {
        return new ErrorResponse(e.getMessage());
    }
}
