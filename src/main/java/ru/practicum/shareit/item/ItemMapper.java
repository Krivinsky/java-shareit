package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.user.User;

import java.util.Objects;

public class ItemMapper {

    public static Item toItem(ItemDtoRequest itemDtoRequest, User user) {
        return new Item(
            itemDtoRequest.getId(),
            itemDtoRequest.getName(),
            itemDtoRequest.getDescription(),
            itemDtoRequest.getAvailable(),
            user,
            null
        );
    }

    public static ItemDtoResponse itemDtoResponse(Item item) {
        return new ItemDtoResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }
}
