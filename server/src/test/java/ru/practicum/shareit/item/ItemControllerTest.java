package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemControllerTest {
    @Autowired
    private BookingController bookingController;

    @Autowired
    private ItemController itemController;

    @Autowired
    private UserController userController;

    static UserDto userDtoNew;

    ItemDto itemDtoNew;

    @BeforeEach
    void setUp() {
        userDtoNew = UserDto.builder()
                .id(1L)
                .name("John")
                .email("john.doe@smile.com")
                .build();

        itemDtoNew = ItemDto.builder()
                .name("ItemName")
                .description("ItemDescription")
                .available(true)
                .build();
    }

    @Test
    void creatItemTest() { //работает
        UserDto userDto = userController.create(userDtoNew);
        ItemDto itemDto = itemController.creatItem(1L, itemDtoNew);
        assertEquals(itemDto.getId(), itemController.getItem(userDto.getId(), itemDto.getId()).getId());
    }

    @Test
    void creatItemTestNotFoundUser() { //работает
        assertThrows(NotFoundException.class, () -> itemController.creatItem(1L, itemDtoNew));
    }

    @Test
    void creatItemTestValidationExceptionEmptyName() { //работает
         itemDtoNew = ItemDto.builder()
                .name("")
                .description("ItemDescription")
                .available(true)
                .build();
        assertThrows(ValidationException.class, () -> itemController.creatItem(1L, itemDtoNew));
    }

    @Test
    void creatItemTestValidationExceptionEmptyDescription() { //работает
        itemDtoNew = ItemDto.builder()
                .name("ItemName")
                .description("")
                .available(true)
                .build();
        assertThrows(ValidationException.class, () -> itemController.creatItem(1L, itemDtoNew));
    }

    @Test
    void updateItemTest() {
        userDtoNew = userController.create(userDtoNew);
        itemDtoNew = itemController.creatItem(1L, itemDtoNew);
        ItemDto itemDtoActual = ItemDto.builder()
                .name("UpdateName")
                .description("UpdateDescription")
                .available(true)
                .build();
        ItemDto itemDtoResult = itemController.updateItem(userDtoNew.getId(), itemDtoActual, 1L);

        assertEquals(itemDtoResult.getName(), "UpdateName");
        assertEquals(itemDtoResult.getDescription(), "UpdateDescription");
    }

    @Test
    void updateItemTestNotFoundItem() {
        userDtoNew = userController.create(userDtoNew);


        assertThrows(NotFoundException.class,
                () -> itemController.updateItem(userDtoNew.getId(), itemDtoNew, 99L));
    }

    @Test
    void getAllTest() {
        UserDto userDto = userController.create(userDtoNew);
        itemController.creatItem(1L, itemDtoNew);
        ItemDto itemDto2 = ItemDto.builder()
                .name("ItemName2")
                .description("ItemDescription2")
                .available(true)
                .build();
        itemController.creatItem(1L, itemDto2);
        List<ItemDto> result = itemController.getAll(userDto.getId(), 0L, 1L);
        assertEquals(result.size(), 1);

    }

    @Test
    void getAllTestNotFoundUser() {
        userController.create(userDtoNew);
        itemController.creatItem(1L, itemDtoNew);
        ItemDto itemDto2 = ItemDto.builder()
                .name("ItemName2")
                .description("ItemDescription2")
                .available(true)
                .build();
        itemController.creatItem(1L, itemDto2);

        assertThrows(NotFoundException.class, () -> itemController.getAll(99L, 0L, 1L));
    }

    @Test
    void getItemTest() {
        UserDto userDto = userController.create(userDtoNew);
        UserDto userTwo = userController.create(UserDto.builder().name("Leo").email("Leo@mail.com").build());
        ItemDto itemDto = itemController.creatItem(1L, itemDtoNew);
        BookingDto bookingDto = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusNanos(100000000))
                .end(LocalDateTime.now().plusNanos(200000000))
                .itemId(itemDto.getId())
                .bookerId(1L)
                .build();
        bookingController.creatBooking(userTwo.getId(), bookingDto);
        assertEquals(itemDto.getId(), itemController.getItem(userDto.getId(), itemDto.getId()).getId());
    }

    @Test
    void getItemTestNotFoundItem() {
        UserDto userDto = userController.create(userDtoNew);
//        ItemDto itemDto = itemController.creatItem(1L, itemDtoNew);
        assertThrows(NotFoundException.class, () -> itemController.getItem(userDto.getId(), 1L));
    }

    @Test
    void search() {
        userController.create(userDtoNew);
        itemController.creatItem(1L, itemDtoNew);
        assertEquals(itemController.search(1L, "Desc", 0L, 1L).size(), 1);  ///////////////////////////////////////////////
    }

    @Test
    void createCommentTest() throws InterruptedException {
        userController.create(userDtoNew);
        UserDto userTwo = userController.create(UserDto.builder().name("Leo").email("Leo@mail.com").build());
        ItemDto itemOne = itemController.creatItem(1L, itemDtoNew);
        BookingDto bookingDto = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusNanos(100000000))
                .end(LocalDateTime.now().plusNanos(200000000))
                .itemId(itemOne.getId())
                .bookerId(1L)
                .build();
        bookingController.creatBooking(userTwo.getId(), bookingDto);
        bookingController.updateBooking(1L, 1L, true);
        Thread.sleep(3000);
        CommentDto commentDto = CommentDto.builder()
                .created(LocalDateTime.now().plusSeconds(1))
                .text("text")
                .build();
        BookingMapper.toBooking(bookingDto);
        itemController.createComment(itemOne.getId(), userTwo.getId(), commentDto);
    }

    @Test
    void createCommentTestWhenValidationException() {
        CommentDto commentDto = CommentDto.builder()
                .text("text")
                .build();
        userController.create(userDtoNew);
        ItemDto itemOne = itemController.creatItem(1L, itemDtoNew);
        UserDto userTwo = userController.create(UserDto.builder().name("Leo").email("Leo@mail.com").build());
        BookingDto bookingDto = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusSeconds(1))
                .end(LocalDateTime.now().plusDays(2))
                .itemId(itemOne.getId())
                .bookerId(1L)
                .build();
        bookingController.creatBooking(userTwo.getId(), bookingDto);
        assertThrows(ValidationException.class,
                () -> itemController.createComment(itemOne.getId(), userTwo.getId(), commentDto));
    }

    @Test
    void deleteItemTest() {
        userController.create(userDtoNew);
        itemController.creatItem(1L, itemDtoNew);
        assertEquals(1, itemController.getAll(1L, 0L, 1L).size());
        itemController.deleteItem(1L);
        assertEquals(0, itemController.getAll(1L, 0L, 1L).size());
    }
}