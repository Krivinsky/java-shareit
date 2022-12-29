package ru.practicum.shareit.item.repository;


import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface ItemRepository {

    Item creat(ItemDtoRequest itemDtoRequest, User user) throws ValidationException;

    List<Item> getAll(Long userId);

    Item update(ItemDtoRequest itemDtoRequest, User user, Long itemId) throws NotFoundException;

    Item getItem(Long itemId) throws NotFoundException;

    List<Item> search(String text);

    //delete
}
