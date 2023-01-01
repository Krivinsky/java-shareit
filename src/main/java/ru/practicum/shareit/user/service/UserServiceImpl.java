package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User create(User user) {

        return userRepository.save(user);
    }

    @Override
    public User update(Long userId, User user) throws NotFoundException {

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


    private void validate(User user) {

    }

    private void emailCheck(User user) {

    }
}
