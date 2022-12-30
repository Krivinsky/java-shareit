package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Map<Long, User> users = new HashMap<>();

    private final UserRepository userRepository;

    public User create(User user) {

//        validate(user);
//        emailCheck(user);

        return userRepository.save(user);
    }

    @Override
    public User update(Long userId, User user) throws UserException, ValidationException, NotFoundException {

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        User userForUpdate = userOptional.get();
        if (Objects.nonNull(user.getName())) {
            userForUpdate.setName(user.getName());
        }
        if (Objects.nonNull(user.getEmail())) {
            userForUpdate.setEmail(user.getEmail());
        }
        return userRepository.save(user);
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

    @Override
    public Set<Item> getUserItems(long userId) throws NotFoundException {   //todo
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        return null;
    }


    private void validate(User user) throws UserException, ValidationException {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("не корректный email");
        }

        for (User u : users.values()) {
            if (Objects.equals(user.getEmail(), u.getEmail())) {
                throw new UserException("Пользователь с таким email уже существует");
            }

        }
    }

    private void emailCheck(User user) throws UserException {
        for (User u : users.values()) {
            if (Objects.equals(user.getEmail(), u.getEmail())) {
                throw new UserException("Пользователь с таким email уже существует");
            }
        }
    }
}
