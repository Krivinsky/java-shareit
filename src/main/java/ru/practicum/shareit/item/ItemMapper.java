package ru.practicum.shareit.item;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.user.UserRepositoryImpl;

import java.util.Objects;

public class ItemMapper {

    public static Item toItem(ItemDtoRequest itemDtoRequest, Long userId) throws NotFoundException {
        if (Objects.isNull(itemDtoRequest.getId())) {
            return new Item(
                    ItemRepositoryImpl.generateId(),
                    itemDtoRequest.getName(),
                    itemDtoRequest.getDescription(),
                    itemDtoRequest.getAvailable(),
                    UserRepositoryImpl.getUserIn(userId),
                    null //todo
            );
        } else {
            return new Item(
                    itemDtoRequest.getId(),
                    itemDtoRequest.getName(),
                    itemDtoRequest.getDescription(),
                    itemDtoRequest.getAvailable(),
                    UserRepositoryImpl.getUserIn(userId),
                    null //todo
            );
        }
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
