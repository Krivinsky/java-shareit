package ru.practicum.shareit.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.ItemEntity;
import ru.practicum.shareit.user.UserEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments", schema = "public")
@Getter @Setter @ToString
public class CommentEntity {

    @Id
    @SequenceGenerator(name = "pk_sequence", schema = "public", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @Column(name = "created", nullable = false)
    private Timestamp created;

}
