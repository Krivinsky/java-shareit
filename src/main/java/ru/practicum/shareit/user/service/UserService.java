package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.StorageException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserService {
    User create(User user) throws UserException, ValidationException, StorageException;

    User update(Long userId, User user) throws UserException, ValidationException;

    User getById(Long userId) throws NotFoundException;

    void delete(Long userId);

    List<User> getAll();
}
