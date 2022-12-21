package ru.practicum.shareit.booking;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.Status;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;


//@Data
@Entity
@Getter @Setter @ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "begin")
    LocalDateTime start;

    @Column(name = "finish")
    LocalDateTime end;


    Item item;

    User booker;

    Status status;
}
