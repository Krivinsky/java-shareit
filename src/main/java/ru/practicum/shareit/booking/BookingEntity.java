package ru.practicum.shareit.booking;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.Status;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemEntity;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(schema = "public", name = "bookings")
public class BookingEntity {

    @Id
    @SequenceGenerator(name = "pk_sequence", schema = "public", sequenceName = "bookings_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    Long id;

    @Column(name = "start_date", nullable = false)
    private Timestamp start;

    @Column(name = "end_date", nullable = false)
    private Timestamp end;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    private UserEntity booker;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    Status status;
}
