package ru.practicum.shareit.booking;

import ru.practicum.shareit.Status;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity

public class Booking {

    @Id
    Long id;

    LocalDateTime start;

    LocalDateTime end;

    Item item;

    User booker;

    Status status;
}
