package ru.practicum.shareit.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOld {

    private Long id;

    private String name;

    private String email;
}
