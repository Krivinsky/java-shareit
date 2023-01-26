package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.exeption.ItemException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UnsupportedState;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.practicum.shareit.booking.Status.APPROVED;
import static ru.practicum.shareit.booking.Status.WAITING;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingControllerTest {
    @Autowired
    private BookingController bookingController;

    @Autowired
    private ItemController itemController;

    @Autowired
    private UserController userController;

    BookingDto bookingDto;

    UserDto userDtoOne;

    UserDto userDtoTwo;

    ItemDto itemDto;

    @BeforeEach
    void setUp() {
        bookingDto = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .itemId(1L)
                .bookerId(1L)
                .build();

        userDtoOne = UserDto.builder()
                .name("John")
                .email("john.doe@smile.com")
                .build();

        userDtoTwo = UserDto.builder()
                .name("Leo")
                .email("Lep.1@smile.com")
                .build();

        itemDto = ItemDto.builder()
                .id(1L)
                .name("ItemName")
                .description("ItemDescription")
                .available(true)
                .build();
    }

    @Test
    void creatBooking() {
        UserDto userDtoNew = userController.create(userDtoOne);
        UserDto userDtoNew2 = userController.create(userDtoTwo);
        itemController.creatItem(userDtoNew.getId(), itemDto);
        BookingDtoResponse bookingDtoResponse1 = bookingController.creatBooking(userDtoNew2.getId(), bookingDto);
        assertEquals(bookingController.getBooking(userDtoNew2.getId(), bookingDtoResponse1.getId()).getId(), 1L);
    }

    @Test
    void creatBookingWhenOwnItem() {
        userController.create(userDtoOne);
        itemController.creatItem(1L, itemDto);
        assertThrows(NotFoundException.class, () -> bookingController.creatBooking(1L, bookingDto));
    }

    @Test
    void creatBookingWhenNotFoundBooking() {
        assertThrows(NotFoundException.class,
                () -> bookingController.creatBooking(1L, bookingDto));
    }

    @Test
    void creatBookingWhenItemException() {
        BookingDto bookingDtoForException = BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now().plusDays(2))
                .end(LocalDateTime.now().plusDays(1))
                .itemId(1L)
                .bookerId(1L)
                .build();
        UserDto userDtoNew = userController.create(userDtoOne);
        UserDto userDtoNew2 = userController.create(userDtoTwo);
        itemController.creatItem(userDtoNew.getId(), itemDto);
        assertThrows(ItemException.class,
                () -> bookingController.creatBooking(userDtoNew2.getId(), bookingDtoForException));
    }

    @Test
    void updateBooking() {
        UserDto userDtoNew = userController.create(userDtoOne);
        UserDto userDtoNew2 = userController.create(userDtoTwo);
        itemController.creatItem(userDtoNew.getId(), itemDto);
        BookingDtoResponse bookingDtoResponse1 = bookingController.creatBooking(userDtoNew2.getId(), bookingDto);
        assertEquals(bookingController.getBooking(userDtoNew2.getId(), bookingDtoResponse1.getId()).getStatus(), WAITING);
        bookingController.updateBooking(userDtoNew.getId(), bookingDtoResponse1.getId(), true);
        assertEquals(bookingController.getBooking(userDtoNew2.getId(), bookingDtoResponse1.getId()).getStatus(), APPROVED);
    }

    @Test
    void updateBookingWhenNotFoundBooking() {
        assertThrows(NotFoundException.class,
                () -> bookingController.updateBooking(1L, 1L, true));
    }

    @Test
    void getBooking() {
        UserDto userDtoNew = userController.create(userDtoOne);
        UserDto userDtoNew2 = userController.create(userDtoTwo);
        itemController.creatItem(userDtoNew.getId(), itemDto);
        BookingDtoResponse bookingDtoResponse1 = bookingController.creatBooking(userDtoNew2.getId(), bookingDto);
        assertEquals(bookingController.getBooking(userDtoNew2.getId(), bookingDtoResponse1.getId()).getId(), 1L);
    }

    @Test
    void getAllTest() {
        UserDto userDtoNew = userController.create(userDtoOne);
        UserDto userDtoNew2 = userController.create(userDtoTwo);
        itemController.creatItem(userDtoNew.getId(), itemDto);
        bookingController.creatBooking(userDtoNew2.getId(), bookingDto);
        assertEquals(bookingController.getAll(userDtoNew2.getId(), "ALL", 0L, 10L).size(), 1);
        assertEquals(bookingController.getAll(userDtoNew2.getId(), "CURRENT", 0L, 10L).size(), 0);
        assertEquals(bookingController.getAll(userDtoNew2.getId(), "PAST", 0L, 10L).size(), 0);
        assertEquals(bookingController.getAll(userDtoNew2.getId(), "FUTURE", 0L, 10L).size(), 1);
        assertEquals(bookingController.getAll(userDtoNew2.getId(), "WAITING", 0L, 10L).size(), 1);
        assertEquals(bookingController.getAll(userDtoNew2.getId(), "REJECTED", 0L, 10L).size(), 0);
    }

    @Test
    void getAllTestNotValidation() {
        assertThrows(NotFoundException.class,
                () -> bookingController.getAll(1L, "ALL", 1L, 10L));
    }

    @Test
    void getAllTestUnknownState() {
        UserDto userDtoNew = userController.create(userDtoOne);
        UserDto userDtoNew2 = userController.create(userDtoTwo);
        itemController.creatItem(userDtoNew.getId(), itemDto);
        bookingController.creatBooking(userDtoNew2.getId(), bookingDto);
        assertThrows(UnsupportedState.class,
                () -> bookingController.getAll(userDtoNew2.getId(), "Unknown", 0L, 10L));
    }

    @Test
    void getOwnerItemsAllTest() {
        UserDto userDtoNew = userController.create(userDtoOne);
        UserDto userDtoNew2 = userController.create(userDtoTwo);
        itemController.creatItem(userDtoNew.getId(), itemDto);
        bookingController.creatBooking(userDtoNew2.getId(), bookingDto);

        List<BookingDtoResponse> all = bookingController.getOwnerItemsAll(userDtoNew.getId(), "ALL", 0L, 10L);
        assertEquals(all.size(), 1);
        assertEquals(bookingController.getAll(userDtoNew2.getId(), "ALL", 0L, 10L).size(), 1);
        assertEquals(bookingController.getOwnerItemsAll(userDtoNew2.getId(), "CURRENT", 0L, 10L).size(), 0);
        assertEquals(bookingController.getOwnerItemsAll(userDtoNew2.getId(), "PAST", 0L, 10L).size(), 0);
        assertEquals(bookingController.getOwnerItemsAll(userDtoNew2.getId(), "FUTURE", 0L, 10L).size(), 0);
        assertEquals(bookingController.getOwnerItemsAll(userDtoNew2.getId(), "WAITING", 0L, 10L).size(), 0);
        assertEquals(bookingController.getOwnerItemsAll(userDtoNew2.getId(), "REJECTED", 0L, 10L).size(), 0);
    }
}