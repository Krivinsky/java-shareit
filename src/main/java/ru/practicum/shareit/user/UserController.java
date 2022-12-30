package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.StorageException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private UserRepository userRepository;

    @PostMapping
    public UserDto creat(@RequestBody UserDto userDto) throws UserException, ValidationException, StorageException, NotFoundException {
        User user = userService.create(UserMapper.toUser(userDto));
        log.info("создан пользователь с ID - " + user.getId());
        return UserMapper.toUserDto(user);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable Long userId) throws UserException, ValidationException, NotFoundException {
        User user = userService.update(userId, UserMapper.toUser(userDto));
        log.info("обновлен пользователь с ID - " + user.getId());
        return UserMapper.toUserDto(user);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) throws NotFoundException {
        User user = userService.getById(userId);
        log.info("получен пользователь с ID - " + user.getId());
        return UserMapper.toUserDto(user);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        List<UserDto> usersResp = new ArrayList<>();
        List<User> users = userService.getAll();
        for (User user : users) {
            usersResp.add(UserMapper.toUserDto(user));
        }

        log.info("получен список из "  + usersResp.size() + " пользователей ");
        return usersResp;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) throws NotFoundException {
        log.info("удален пользователь с ID " + userId);
        userService.delete(userId);
    }

    @PutMapping  //  ("/{userId}")
    public void put() throws NotFoundException {
        throw new NotFoundException("такого эндпоинта не существует");
    }
}
