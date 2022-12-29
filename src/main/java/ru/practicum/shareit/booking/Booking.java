package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.Status;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
public class Booking {

    Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private Item item;

    private User booker;

    Status status;
}
