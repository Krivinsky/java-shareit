package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {

    private  Long id;

    @NotBlank(message = "некорректное имя")
    private  String name;

    @Email(message = "некорректный email")
    @NotBlank
    private  String email;
}
