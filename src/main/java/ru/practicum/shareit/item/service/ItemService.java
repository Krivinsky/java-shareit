package ru.practicum.shareit.item.service;

import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface ItemService {
    Item creatItem(ItemDtoRequest itemDtoRequest, User user) throws ValidationException, NotFoundException;

    Item updateItem(ItemDtoRequest itemDtoRequest, User user, Long itemId) throws NotFoundException;

    List<ItemDtoResponse> getAll(Long userId) throws NotFoundException;

    ItemDtoResponse getItemComment(Long itemId, Long userId) throws NotFoundException;

    Item getItem(Long itemId) throws NotFoundException;

    List<Item> search(String text);

    CommentDto creatComment(Long userId, Long itemId, CommentDto commentDto) throws NotFoundException;
}
