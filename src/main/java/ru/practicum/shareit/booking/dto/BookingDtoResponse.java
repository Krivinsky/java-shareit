package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDtoResponse {
    private Long id;

    @Future
    private LocalDateTime start;

    @Future
    private LocalDateTime end;

    private Status status;

    private UserDto booker;

    private ItemDto item;

}
