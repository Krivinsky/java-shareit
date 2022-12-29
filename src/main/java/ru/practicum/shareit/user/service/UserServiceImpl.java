package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDtoRequest;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User create(User user) throws UserException, ValidationException {
        return userRepository.creatUser(user);
    }

    @Override
    public User update(Long userId, User user) throws UserException, ValidationException {
        return userRepository.update(user, userId);
    }

    @Override
    public User getById(Long userId) throws NotFoundException {
        return userRepository.getUser(userId);
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAllUsers();
    }
}
