package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDtoRequest;
import ru.practicum.shareit.user.dto.UserDtoResponse;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;

import java.util.Objects;

public class UserMapper {

   public static User toUserFromRequest(UserDtoRequest userDtoRequest, Long id) {
        if (Objects.isNull(id)) {
            return new User(
                    UserRepositoryImpl.generateId(),
                    userDtoRequest.getName(),
                    userDtoRequest.getEmail()
            );
        } else {
            return new User(
                    id,
                    userDtoRequest.getName(),
                    userDtoRequest.getEmail()
            );
        }
    }

    public static UserDtoResponse toUserDtoResponse(User user) {
        return new UserDtoResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
