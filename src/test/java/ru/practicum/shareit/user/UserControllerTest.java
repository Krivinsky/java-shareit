package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;
import ru.practicum.shareit.exeption.ConflictException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String PATH = "/users";
    private static final String PATH_WITH_ID = "/users/1";
    private static final long USER_ID = 1L;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    UserDto userDto;
    User user;
    List<UserDto> userList;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(1L)
                .name("John")
                .email("john.doe@smile.com")
                .build();

        user = User.builder().build();
        userList = new ArrayList<>();
        userList.add(userDto);
    }

    @Test
    void create_whenUserFound_thenReturnedUser() throws Exception {
        when(userService.createUser(any())).thenReturn(userDto);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getContentFromFile("user/request/create.json"))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(getContentFromFile("user/response/created.json")
                ));
    }

    @Test
    void update_whenUserFound_thenReturnedUser() throws Exception {
        userDto = UserDto.builder().id(1L).name("John2").email("john2.doe@smile.com").build();
        when(userService.updateUser(eq(USER_ID), any())).thenReturn(userDto);

        mockMvc.perform(patch(PATH_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContentFromFile("user/request/update.json"))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(getContentFromFile("user/response/updated.json")
                ));
    }

    @Test
    void update_whenBadRequest_thenThrowConflictException() throws Exception {
        userDto = UserDto.builder().id(1L).name("John2").email("john2.doe@smile.com").build();
        when(userService.updateUser(eq(USER_ID), any())).thenThrow(ConflictException.class);

        mockMvc.perform(patch(PATH_WITH_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getContentFromFile("user/request/update.json"))
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getUser_whenUserFound_thenReturnedUser() throws Exception {
        long userId = 1L;
        when(userService.getById(userId))
                .thenReturn(userDto);

        mockMvc.perform(get(PATH_WITH_ID)
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())));
    }

    @Test
    void getUser_whenUserNotFound_thenNotFoundExceptionThrow() {
        long userId = 99L;
        when(userService.getById(userId))
                .thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    void getAllUser() throws Exception {
        when(userService.getAll()).thenReturn(List.of(userDto));

        mockMvc.perform(get("/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(userDto.getName())))
                .andExpect(jsonPath("$[0].email", is(userDto.getEmail())));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete(PATH_WITH_ID))
                .andExpect(status().isOk());
        verify(userService).delete(USER_ID);
    }

    private String getContentFromFile(final String fileName) {
        try {
            return Files.readString(ResourceUtils.getFile("classpath:" + fileName).toPath(), StandardCharsets.UTF_8);
        } catch (final IOException e) {
            throw new RuntimeException("файл не найден",e);
        }
    }
}