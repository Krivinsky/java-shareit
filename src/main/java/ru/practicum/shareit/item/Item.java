package ru.practicum.shareit.item;

import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

    Long id;

    String name;

    String description;

    Boolean available;

    User owner;

    ItemRequest request;
}
