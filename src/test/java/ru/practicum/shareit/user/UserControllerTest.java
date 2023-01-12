package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getAllUsers() {
        User user = User.builder().build();
        List <User> userList = new ArrayList<>(){{add(user);}};

        Mockito.when(userService.getAll()).thenReturn(userList);

    }

    @Test
    void deleteUser() {
    }
}