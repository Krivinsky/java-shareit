package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(BookingController.class)
class BookingControllerWebMvcTest {
    private static final String PATH = "/bookings";
    private static final String PATH_WITH_ID = "/bookings/1";
    private static final long BOOKING_ID = 1L;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    BookingDto bookingDto;

    BookingDtoResponse bookingDtoResponse;

    UserDto userDto;

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

        userDto = UserDto.builder()
                .id(1L)
                .name("John")
                .email("john.doe@smile.com")
                .build();

        itemDto = ItemDto.builder()
                .id(1L)
                .name("ItemName")
                .description("ItemDescription")
                .available(true)
                .build();

        bookingDtoResponse = BookingDtoResponse.builder()
                        .id(1L)
                        .start(LocalDateTime.now().plusDays(1))
                        .end(LocalDateTime.now().plusDays(2))
                        .item(new Item())
                        .booker(new User())
                        .build();
    }

    @Test
    void creatBookingTest() throws Exception {
        when(bookingService.creatBooking(any(), anyLong())).thenReturn(bookingDtoResponse);

        mockMvc.perform(post(PATH)
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDtoResponse.getId()), Long.class));
    }

    @Test
    void updateBookingTest() throws Exception {
        when(bookingService.updateBooking(anyLong(),anyLong(), any())).thenReturn(bookingDtoResponse);

        mockMvc.perform(patch(PATH_WITH_ID + "?approved=true")
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDtoResponse.getId()), Long.class));
    }

    @Test
    void getBookingTest() throws Exception {
        when(bookingService.getBooking(anyLong(),anyLong())).thenReturn(bookingDtoResponse);

        mockMvc.perform(get(PATH_WITH_ID)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookingDtoResponse.getId()), Long.class));
    }

    @Test
    void getAllTest() throws Exception {
        when(bookingService.getAll(anyLong(), anyString(), anyLong(),anyLong())).thenReturn(List.of(bookingDtoResponse));

        mockMvc.perform(get(PATH + "?from=1&size=1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(bookingDtoResponse.getId()), Long.class));
    }

    @Test
    void getOwnerItemsAllTest() throws Exception {
        when(bookingService.getOwnerItemsAll(anyLong(), anyString(), anyLong(),anyLong())).thenReturn(List.of(bookingDtoResponse));

        mockMvc.perform(get(PATH + "/owner?from=1&size=1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(bookingDtoResponse.getId()), Long.class));
    }
}