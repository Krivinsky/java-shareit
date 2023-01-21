package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest({UserMapper.class})
class UserMapperTest {

    private UserMapper userMapper;

    @Test
    void toUser() {
        UserDto userDtoOriginal = UserDto.builder().build();

        userDtoOriginal.setId(1L);
        userDtoOriginal.setName("John2");
        userDtoOriginal.setEmail("john2.doe@smile.com");

        User userResult = UserMapper.toUser(userDtoOriginal);
        assertNotNull(userDtoOriginal);
        assertEquals(userDtoOriginal.getId(), userResult.getId());
        assertEquals(userDtoOriginal.getName(), userResult.getName());
        assertEquals(userDtoOriginal.getEmail(), userResult.getEmail());
    }

    @Test
    void toUserDto() {
        User userOriginal = new User(1L,"John2","john2.doe@smile.com");

        UserDto userDtoResult = UserMapper.toUserDto(userOriginal);
        assertNotNull(userDtoResult);
        assertEquals(userDtoResult.getId(), userOriginal.getId());
        assertEquals(userDtoResult.getName(), userOriginal.getName());
        assertEquals(userDtoResult.getEmail(), userOriginal.getEmail());
    }
}