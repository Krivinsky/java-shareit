package ru.practicum.shareit.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.ConflictException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new ConflictException("Такой пользователь уже существует");
        }
    }

    @Override
    public User update(Long userId, UserDto userDto) throws NotFoundException {

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        User userForUpdate = userOptional.get();
        if (Objects.nonNull(userDto.getName())) {
            userForUpdate.setName(userDto.getName());
        }
        if (Objects.nonNull(userDto.getEmail())) {
            userForUpdate.setEmail(userDto.getEmail());
        }
        try {
            return userRepository.save(userForUpdate);
        } catch (RuntimeException ex) {
            throw new ConflictException("Такой пользователь уже существует");
        }
    }

    @Override
    public User getById(Long userId) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        return userOptional.get();
    }

    @Override
    public void delete(Long userId) throws NotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userRepository.findAll());
    }

}
