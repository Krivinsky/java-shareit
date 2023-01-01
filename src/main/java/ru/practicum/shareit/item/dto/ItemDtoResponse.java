package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.comment.CommentDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemDtoResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final Boolean available;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<CommentDto> comments = new ArrayList<>();
}
