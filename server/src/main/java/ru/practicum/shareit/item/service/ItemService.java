package ru.practicum.shareit.item.service;

import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto creatItem(ItemDto itemDto, Long userId) throws ValidationException, NotFoundException;

    ItemDto updateItem(ItemDto itemDto, Long userId, Long itemId) throws NotFoundException;

    List<ItemDto> getAll(Long userId, int from, int size) throws NotFoundException;

    ItemDto getById(Long itemId, Long userId) throws NotFoundException;

    List<ItemDto> search(String text, Long from, Long size);

    CommentDto creatComment(Long userId, Long itemId, CommentDto commentDto) throws NotFoundException, ValidationException;

    void delete(Long itemId);
}
