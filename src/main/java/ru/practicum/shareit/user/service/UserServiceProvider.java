package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.StorageException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserEntity;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserServiceProvider implements UserService {

    @Override
    public User create(User user) throws StorageException {
        try {
            return mapper.toUser(repositoy.save(mapper.toEntity(user)));
        } catch (Exception ex) {
            throw new StorageException("");
        }
    }

    @Override
    public User update(Long userId, User user) throws StorageException {
        try {
            UserEntity stored = repository.findById(userId)
                    .orElseThrow(NotFoundException::new);
            mapper.updateEntity(user, stored);
            return mapper.tUser(repository.save(stored));
        } catch (NotFoundException e) {
            throw new StorageException("");
        }
    }

    @Override
    public User getById(Long userId) {
        return repository.findById(userId)
                .map(mapper::toUser)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll().stream()
                .map(mapper::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId) {
        repository.deleteById(userId);
    }
}
