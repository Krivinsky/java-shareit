package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDtoRequest;
import ru.practicum.shareit.user.dto.UserDtoResponse;

import java.util.Objects;

public class UserMapper {

   public static User toUserFromRequest(UserDtoRequest userDtoRequest) {
       return new User(
           userDtoRequest.getId(),
           userDtoRequest.getName(),
           userDtoRequest.getEmail()
       );
   }

    public static UserDtoResponse toUserDtoResponse(User user) {
        return new UserDtoResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
