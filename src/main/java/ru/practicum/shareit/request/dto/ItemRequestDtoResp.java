package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ItemRequestDtoResp {
    Long id;
    String description;
    LocalDateTime created;
    List<ItemDto> listOfAnswers;
}
