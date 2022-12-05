package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.dto.UserDtoRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public User creatUser(UserDtoRequest userDtoRequest) throws UserException, ValidationException {
        return userRepository.creatUser(userDtoRequest);
    }

    @Override
    public User update(UserDtoRequest userDtoRequest, Long userId) throws UserException, ValidationException {
        return userRepository.update(userDtoRequest, userId);
    }

    @Override
    public User getUser(Long userId) throws NotFoundException {
        return userRepository.getUser(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
