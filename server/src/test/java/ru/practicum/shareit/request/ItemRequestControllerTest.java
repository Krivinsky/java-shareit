package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemRequestControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private ItemRequestController itemRequestController;

    private UserDto userDto;

    private ItemRequestDto itemRequestDto;

    @BeforeEach
    void setUp() {

        userDto = UserDto.builder()
                .id(1L)
                .name("John")
                .email("john.doe@smile.com")
                .build();

        itemRequestDto = ItemRequestDto.builder()
                .id(1L)
                .description("itemDTODescription")
                .build();
    }

    @Test
    void createTest() {
        UserDto user = userController.create(userDto);
        ItemRequest itemRequest = itemRequestController.create(user.getId(), itemRequestDto);
        assertEquals(1L, itemRequestController.getItemRequestById(itemRequest.getId(), user.getId()).getId());
    }

    @Test
    void createTestWhenNotFound() {
        assertThrows(NotFoundException.class, () -> itemRequestController.create(1L, itemRequestDto));
    }

    @Test
    void getByUserTest() {
        UserDto user = userController.create(userDto);
        itemRequestController.create(user.getId(), itemRequestDto);
        assertEquals(1, itemRequestController.getByUser(user.getId()).size());
    }

    @Test
    void getAllTest() {
        UserDto user = userController.create(userDto);
        itemRequestController.create(user.getId(), itemRequestDto);
        assertEquals(0, itemRequestController.getAll(user.getId(),0, 10).size());
        UserDto user2 = userController.create(UserDto.builder().name("Leo").email("Leo@email.com").build());
        assertEquals(1, itemRequestController.getAll(user2.getId(), 0, 10).size());
    }

    @Test
    void getItemRequestById() {
        UserDto user = userController.create(userDto);
        ItemRequest itemRequest = itemRequestController.create(user.getId(), itemRequestDto);
        assertEquals(1L, itemRequestController.getItemRequestById(itemRequest.getId(), user.getId()).getId());
    }
}