package ru.practicum.shareit.item.service;

import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    Item creatItem(ItemDto itemDto, Long userId) throws ValidationException, NotFoundException;

    Item updateItem(ItemDto itemDto, Long userId, Long itemId) throws NotFoundException;

    List<ItemDto> getAll(Long userId) throws NotFoundException;

    ItemDto getById(Long itemId, Long userId) throws NotFoundException;

    Item getItem(Long itemId) throws NotFoundException;

    List<Item> search(String text);

    CommentDto creatComment(Long userId, Long itemId, CommentDto commentDto) throws NotFoundException, ValidationException;
}
