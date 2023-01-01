package ru.practicum.shareit.item;

import lombok.*;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "items", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @SequenceGenerator(name = "pk_sequence", schema = "public", sequenceName = "items_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private Boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "request_id")
    private List<Item> request;
}


