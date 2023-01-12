package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exeption.ConflictException;
import ru.practicum.shareit.user.User;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserServiceImpl userService;

    @Test
    void create() {
        User user = User.builder()
                .name("User Name")
                .email("User@mail.com")
                .build();

        Mockito
                .when(userService.create(Mockito.any(User.class)))
                .thenReturn(user);

        userService.create(user);


        Mockito
                .when(userService.create(Mockito.any(User.class)))
                .thenThrow(new ConflictException("Такой пользователь уже существует"));

        final ConflictException exception = Assertions.assertThrows(
                ConflictException.class,
                () -> userService.create(user));

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