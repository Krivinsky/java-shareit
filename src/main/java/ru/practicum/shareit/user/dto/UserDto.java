package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserDto {

    Long id;

    @NotBlank(message = "некорректное имя")
    String name;

    @Email(message = "некорректный email")
    String email;
}
