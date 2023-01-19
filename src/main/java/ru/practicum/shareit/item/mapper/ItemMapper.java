package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShortDto;

import java.util.ArrayList;

public class ItemMapper {

    public static Item toItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequest() != null ? item.getRequest().getId() : null)
                .comments(new ArrayList<>())
                .build();
    }

    public static ItemShortDto toItemShortDto(Item item) {
        return ItemShortDto.builder()
                .id(item.getId())
                .name(item.getName())
                .ownerId(item.getOwner().getId())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequest().getId())
                .build();
    }
}
