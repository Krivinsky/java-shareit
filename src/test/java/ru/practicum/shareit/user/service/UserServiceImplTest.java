package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exeption.ConflictException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserServiceImpl userService;

    UserDto userDto;

    User user;
    List<UserDto> userList;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(1L)
                .name("John")
                .email("john.doe@mail.com")
                .build();

        user = User.builder().build();
        userList = new ArrayList<>(){{add(userDto);}};
    }

    @Test
    void create() {
        User user = User.builder()
                .name("User Name")
                .email("User@mail.com")
                .build();

        Mockito
                .when(userService.create(Mockito.any(UserDto.class)))
                .thenReturn(userDto);

        userService.create(userDto);


        Mockito
                .when(userService.create(Mockito.any(UserDto.class)))
                .thenThrow(new ConflictException("Такой пользователь уже существует"));

        final ConflictException exception = Assertions.assertThrows(
                ConflictException.class,
                () -> userService.create(userDto));

        Assertions.assertEquals("Такой пользователь уже существует", exception.getMessage());
        
    }

    @Test
    void update() {
    }

    @Test
    void getById() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }
}