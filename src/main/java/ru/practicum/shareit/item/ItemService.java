package ru.practicum.shareit.item;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

import java.util.List;

public interface ItemService {
     Item creatItem(ItemDtoRequest itemDtoRequest, Long userId) throws NotFoundException, ValidationException;

    Item updateItem(ItemDtoRequest itemDtoRequest, Long userId, Long itemId) throws NotFoundException;

    List<ItemDtoResponse> getAll(Long userId);

    Item getItem(Long itemId) throws NotFoundException;

    List <Item> search(String text);
}
