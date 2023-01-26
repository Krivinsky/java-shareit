package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest({BookingMapper.class})
class BookingMapperTest {

    private BookingMapper bookingMapper;

    @Test
    void toBookingTest() {
        BookingDto bookingDtoOriginal = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .itemId(1L)
                .bookerId(1L)
                .build();

        Booking bookingResult = BookingMapper.toBooking(bookingDtoOriginal);
        assertNotNull(bookingResult);
        assertEquals(bookingResult.getId(), bookingDtoOriginal.getId());
        assertEquals(bookingResult.getStart(), bookingDtoOriginal.getStart());
        assertEquals(bookingResult.getEnd(), bookingDtoOriginal.getEnd());
    }

    @Test
    void toBookingDtoTest() {
        Booking bookingOriginal = Booking.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .item(new Item())
                .booker(new User())
                .build();

        BookingDto bookingDtoResult = BookingMapper.toBookingDto(bookingOriginal);
        assertNotNull(bookingDtoResult);
        assertEquals(bookingDtoResult.getId(), bookingOriginal.getId());
        assertEquals(bookingDtoResult.getStart(), bookingOriginal.getStart());
        assertEquals(bookingDtoResult.getEnd(), bookingOriginal.getEnd());
    }

    @Test
    void toBookingFromBookingDtoResponse() {
        BookingDtoResponse bookingDtoResponseOriginal = BookingDtoResponse.builder().build();
        bookingDtoResponseOriginal.setId(1L);
        bookingDtoResponseOriginal.setStart(LocalDateTime.now());
        bookingDtoResponseOriginal.setStatus(Status.WAITING);

        Booking bookingResult = BookingMapper.toBooking(bookingDtoResponseOriginal);

        assertNotNull(bookingResult);
        assertEquals(bookingResult.getId(), bookingDtoResponseOriginal.getId());
    }
}