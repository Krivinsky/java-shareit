package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {
    private Long id;

    @Future
    private LocalDateTime start;

    @Future
    private LocalDateTime end;

    private Long itemId;

    private Long bookerId;
}
