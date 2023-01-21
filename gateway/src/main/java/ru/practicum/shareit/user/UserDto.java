package ru.practicum.shareit.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Builder
@Getter
@Setter
public class UserDto {

    private  Long id;

    @NotBlank(message = "некорректное имя")
    private  String name;

    @Email(message = "некорректный email")
    @NotBlank
    private  String email;
}
