package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.dto.UserDtoRequest;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    protected static Long generatedId = 0L;

    protected static Long generateId() {
        return ++generatedId;
    }

    @Override
    public User creatUser(UserDtoRequest userDtoRequest) throws UserException, ValidationException {
        validate(userDtoRequest);
        emailCheck(userDtoRequest);
        userDtoRequest.setId(generateId());
        User user = UserMapper.toUserFromRequest(userDtoRequest);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(UserDtoRequest userDtoRequest, Long userId) throws UserException {
        emailCheck(userDtoRequest);
        User user1 = users.get(userId);
        if (Objects.nonNull(userDtoRequest.getName())) {
            user1.setName(userDtoRequest.getName());
        }
        if (Objects.nonNull(userDtoRequest.getEmail())) {
            user1.setEmail(userDtoRequest.getEmail());
        }

        users.put(userId, user1);
        return user1;
    }

    @Override
    public User getUser(Long userId) throws NotFoundException {
        User user = users.get(userId);
        if (Objects.nonNull(user)) {
            return user;
        }
        throw new NotFoundException("Пользователь не найден");
    }

    @Override
    public void deleteUser(Long userId) {
        users.remove(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    private void validate(UserDtoRequest userDtoRequest) throws UserException, ValidationException {
        if (userDtoRequest.getEmail() == null || userDtoRequest.getEmail().isEmpty() || !userDtoRequest.getEmail().contains("@")) {
            throw new ValidationException("не корректный email");
        }

        for (User u : users.values()) {
            if (Objects.equals(userDtoRequest.getEmail(), u.getEmail())) {
                throw new UserException("Пользователь с таким email уже существует");
            }

        }
    }

    private void emailCheck(UserDtoRequest userDtoRequest) throws UserException {
        for (User u : users.values()) {
            if (Objects.equals(userDtoRequest.getEmail(), u.getEmail())) {
                throw new UserException("Пользователь с таким email уже существует");
            }
        }
    }
}
