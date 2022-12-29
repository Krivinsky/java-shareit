package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentEntity;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.mapper.UserRepositoryMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Mapper(componentModel = "spring",
        uses = {
                UserRepositoryMapper.class,
                ItemRepositoryMapper.class
        })
public interface ItemRepositoryMapper {


    @Named("toTimestamp")
    default Timestamp toTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    @Mapping(target = "itemId", source = "item.id")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorName", source = "author.name")
    Item.ItemComment toItemComment(CommentEntity entity);

    @Mapping(target = "created", source = "created", qualifiedByName = "toLocalDateTime")
    Comment toComment(CommentEntity entity);

    @Mapping(target = "created", source = "created", qualifiedByName = "toTimestamp")
    CommentEntity toEntity(Comment comment);
}
