package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.Status;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.user.dto.UserDtoResponse;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDtoResponse {
    private Long id;
    private Status status;
    private UserDtoResponse booker;
    private ItemDtoResponse item;
    private LocalDateTime start;
    private LocalDateTime end;
}
