package ru.practicum.shareit.user;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

import javax.persistence.*;


@Entity
@Table(name = "users", schema = "public")
@Getter @Setter @ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;
}
