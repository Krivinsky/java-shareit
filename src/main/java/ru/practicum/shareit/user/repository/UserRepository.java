package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDtoRequest;

import java.util.List;

public interface UserRepository extends JpaRepository <User, Long> {
    User creatUser(User user) throws UserException, ValidationException;

    User update(User user, Long userId) throws UserException, ValidationException;

    User getUser(Long userId) throws NotFoundException;

    void deleteUser(Long userId);

    List<User> getAllUsers();
}
