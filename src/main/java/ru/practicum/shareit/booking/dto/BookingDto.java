package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.Status;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
public class BookingDto {
    private Long id;

    private Status status;

    private Long bookerId;

    private Long itemId;

    private LocalDateTime start;

    private LocalDateTime end;

    private String itemName;
}
