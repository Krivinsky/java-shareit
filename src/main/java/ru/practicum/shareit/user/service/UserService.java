package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    User create(User user);

    User update(Long userId, UserDto userDto) throws NotFoundException;

    User getById(Long userId) throws NotFoundException;

    void delete(Long userId) throws NotFoundException;

    List<User> getAll();
}
