package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.UserMapper;

public class BookingMapper {
    public static Booking toBooking(BookingDto bookingDto) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                null,
                null,
                bookingDto.getStatus()
        );
    }

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getItem().getName(),
                booking.getBooker().getId(),
                booking.getStatus()
        );
    }

    public static BookingDtoResponse bookingDtoResponse(Booking booking) {
        return new BookingDtoResponse(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                ItemMapper.itemDtoResponse(booking.getItem()),
                UserMapper.toUserDto(booking.getBooker()),
                booking.getStatus()
        );
    }
}
