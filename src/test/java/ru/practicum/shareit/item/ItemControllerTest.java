package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void creatItem() {
    }

    @Test
    void updateItem() {
    }

    @Test
    void getAll() {


    }

    @Test
    void getItem() {
    }

    @Test
    void search() {
    }

    @Test
    void create() {
    }
}