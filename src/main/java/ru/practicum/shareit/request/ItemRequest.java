package ru.practicum.shareit.request;

import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
public class ItemRequest {
    Long id;
    String description;
    User requestor;
    LocalDateTime created;
}
