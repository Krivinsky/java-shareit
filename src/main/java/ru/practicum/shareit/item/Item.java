package ru.practicum.shareit.item;

import lombok.*;
import ru.practicum.shareit.Status;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private User owner;

    private ItemRequest request;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class ItemBooking {
        private Long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private Long itemId;
        private Status status;
    }

    @Getter
    @AllArgsConstructor
    public static class ItemComment {
        private final Long id;
        private final String text;
        private final Long itemId;
        private final Long authorId;
        private final String authorName;
        private final LocalDateTime created;
    }

}
