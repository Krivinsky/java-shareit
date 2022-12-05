package ru.practicum.shareit.item;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface ItemService {
     Item creatItem(ItemDtoRequest itemDtoRequest, User user) throws ValidationException;

    Item updateItem(ItemDtoRequest itemDtoRequest, User user, Long itemId) throws NotFoundException;

    List<ItemDtoResponse> getAll(Long userId);

    Item getItem(Long itemId) throws NotFoundException;

    List<Item> search(String text);
}
