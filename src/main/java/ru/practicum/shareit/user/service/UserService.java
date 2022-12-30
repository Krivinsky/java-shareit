package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.StorageException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User create(User user) throws UserException, ValidationException, StorageException, NotFoundException;

    User update(Long userId, User user) throws UserException, ValidationException, NotFoundException;

    User getById(Long userId) throws NotFoundException;

    void delete(Long userId) throws NotFoundException;

    List<User> getAll();

    Set<Item> getUserItems(long userId) throws NotFoundException;
}
