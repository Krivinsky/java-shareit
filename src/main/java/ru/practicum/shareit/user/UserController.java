package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.dto.UserDtoRequest;
import ru.practicum.shareit.user.dto.UserDtoResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDtoResponse creat(@RequestBody UserDtoRequest userDtoRequest) throws UserException, ValidationException {
        User user = userService.creatUser(userDtoRequest);
        log.info("создан пользователь с ID - " + user.getId());
        return UserMapper.toUserDtoResponse(user);
    }

    @PatchMapping("/{userId}")
    public UserDtoResponse update(@RequestBody UserDtoRequest userDtoRequest, @PathVariable Long userId) throws UserException, ValidationException {
        User user = userService.update(userDtoRequest, userId);
        log.info("обновлен пользователь с ID - " + user.getId());
        return UserMapper.toUserDtoResponse(user);
    }

    @GetMapping("/{userId}")
    public UserDtoResponse getUser(@PathVariable Long userId) throws NotFoundException {
        User user = userService.getUser(userId);
        log.info("получен пользователь с ID - " + user.getId());
        return UserMapper.toUserDtoResponse(user);
    }

    @GetMapping
    public List<UserDtoResponse> getAllUsers() {
        List<UserDtoResponse> usersResp = new ArrayList<>();
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            usersResp.add(UserMapper.toUserDtoResponse(user));
        }
        log.info("получен список из "  + usersResp.size() + " пользователей ");
        return usersResp;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("удален пользователь с ID " + userId);
        userService.deleteUser(userId);
    }

}
