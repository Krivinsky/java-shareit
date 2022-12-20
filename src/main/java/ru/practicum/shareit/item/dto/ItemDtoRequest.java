package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDtoRequest {
    Long id;
    String name;
    String description;
    Boolean available;
}
