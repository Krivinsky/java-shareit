package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

class UserServiceImplTest {

//    UserRepository userRepository = Mockito.mock(UserRepository.class);
//    UserServiceImpl userService = new UserServiceImpl(userRepository);

    @Test
    void create() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Mockito
                .when(userService.create(Mockito.any(User.class)))
                .thenReturn(new User());

        
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