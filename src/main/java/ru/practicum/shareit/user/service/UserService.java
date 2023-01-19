package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto) throws NotFoundException;

    UserDto getById(Long userId) throws NotFoundException;

    void delete(Long userId) throws NotFoundException;

    List<UserDto> getAll();
}
