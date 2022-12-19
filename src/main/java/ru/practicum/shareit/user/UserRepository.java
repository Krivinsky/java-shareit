package ru.practicum.shareit.user;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.dto.UserDtoRequest;

import java.util.List;

public interface UserRepository {
    User creatUser(UserDtoRequest userDtoRequest) throws UserException, ValidationException;

    User update(UserDtoRequest userDtoRequest, Long userId) throws UserException, ValidationException;

    User getUser(Long userId) throws NotFoundException;

    void deleteUser(Long userId);

    List<User> getAllUsers();
}
