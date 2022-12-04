package ru.practicum.shareit.item;


import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDtoRequest;

import java.util.List;

public interface ItemRepository {

    Item creat (ItemDtoRequest itemDtoRequest, Long userId) throws NotFoundException, ValidationException;

    List<Item> getAll(Long userId);

    Item update(ItemDtoRequest itemDtoRequest, Long userId, Long itemId) throws NotFoundException;

    Item getItem(Long itemId) throws NotFoundException;

    List <Item> search(String text);

    //delete
}
