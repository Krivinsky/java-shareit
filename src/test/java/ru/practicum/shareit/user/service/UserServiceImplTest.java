package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.practicum.shareit.exeption.ConflictException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    User user;
    List<UserDto> userList;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        when(userRepository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    void createUser_whenUserNoValid_thenConflictExceptionThrow() {
        User userToSave = new User();
        userToSave.setId(1L);
        userToSave.setName("Name");
        userToSave.setEmail("a1@emal.com");
        when(userRepository.save(any())).thenThrow(ConflictException.class);
        assertThrows(ConflictException.class, () -> userService.createUser(UserMapper.toUserDto(userToSave)));
    }

    @Test
    void createUser_whenUserValid_thenSavedUser() {
        User userToSave = new User();
        userToSave.setId(1L);
        userToSave.setName("John");
        userToSave.setEmail("john.doe@emal.com");
        UserDto actualUserDto = userService.createUser(UserMapper.toUserDto(userToSave));
        assertNotNull(actualUserDto);
        assertEquals(actualUserDto.getId(), UserMapper.toUserDto(userToSave).getId());
        assertEquals(actualUserDto.getName(), UserMapper.toUserDto(userToSave).getName());
    }

    @Test
    void updateUser_whenUserFound_thenUserUpdate() {
        User userNew = new User();
        userNew.setId(1L);
        when(userRepository.findById(userNew.getId())).thenReturn(Optional.of(userNew));

        UserDto userDto = UserDto.builder().id(1L).name("John").email("john.doe@smile.com").build();

        UserDto userDtoResult = userService.updateUser(1L, userDto);
        assertNotNull(userDtoResult);
        assertEquals(userDto.getId(), userDtoResult.getId());
        assertEquals(userDto.getName(), userDtoResult.getName());
        assertEquals(userDto.getEmail(), userDtoResult.getEmail());


    }

    @Test
    void updateUser_whenUserNotFound_thenUserUpdate() {
        Long userId = 3L;
        UserDto oldUserDto = UserDto.builder().id(3L).name("John3").email("john3.doe@mail.com").build();
        UserDto newUserDto = UserDto.builder().id(4L).name("John4").email("john4.doe@mail.com").build();

        assertThrows(NotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    void getById_whenUserFound_thenReturnedUser() {
        long userId = 0L;
        User expectedUser = new User(1L, "Name", "1@emal.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        UserDto actualUserDto = userService.getById(userId);
        User actualUser = UserMapper.toUser(actualUserDto);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getName(), actualUser.getName());

    }

    @Test
    void getById_whenUserNotFound_thenNotFoundExceptionThrow() {
        long userId = 0L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    void delete() {  //работает
        Long userId = 1L;
        User expectedUser = new User(1L, "Name", "1@emal.com");
        userRepository.save(expectedUser);
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        userService.delete(expectedUser.getId());

        verify(userRepository, Mockito.times(1))
                .deleteById(expectedUser.getId());
    }

    @Test
    void getAll() {
        User expectedUser = new User(1L, "Name", "1@emal.com");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(expectedUser));

        List<UserDto> list = userService.getAll();
        assertNotNull(list.get(0));
        assertEquals(list.get(0).getId(), expectedUser.getId());
    }
}