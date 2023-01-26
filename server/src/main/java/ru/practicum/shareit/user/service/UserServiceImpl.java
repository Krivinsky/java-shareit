package ru.practicum.shareit.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.ConflictException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        try {
            return UserMapper.toUserDto(userRepository.save(user));
        } catch (RuntimeException ex) {
            throw new ConflictException("Такой пользователь уже существует");
        }
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) throws NotFoundException {

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
            return UserMapper.toUserDto(userRepository.save(userForUpdate));
        } catch (RuntimeException ex) {
            throw new ConflictException("Ошибка входных данных");
        }
    }

    @Override
    public UserDto getById(Long userId) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        return UserMapper.toUserDto(userOptional.get());
    }

    @Override
    public void delete(Long userId) throws NotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
