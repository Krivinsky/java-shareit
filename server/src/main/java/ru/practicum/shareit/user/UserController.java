package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exeption.ConflictException;
import ru.practicum.shareit.exeption.ErrorResponse;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.info("создан пользователь с ID - " + userDto.getId());
        return userService.createUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable Long userId) throws NotFoundException {
        UserDto userDto1 = userService.updateUser(userId, userDto);
        log.info("обновлен пользователь с ID - " + userDto1.getId());
        return userDto1;
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) throws NotFoundException {
        UserDto userDto = userService.getById(userId);
        log.info("получен пользователь с ID - " + userDto.getId());
        return userDto;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("получен список пользователей ");
        return userService.getAll();
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) throws NotFoundException {
        log.info("удален пользователь с ID " + userId);
        userService.delete(userId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleServerException(final ConflictException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
