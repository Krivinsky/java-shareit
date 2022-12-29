package ru.practicum.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Comment {

    private Long id;

    private String text;

    private Item item;

    private User author;

    private LocalDateTime created;
}
