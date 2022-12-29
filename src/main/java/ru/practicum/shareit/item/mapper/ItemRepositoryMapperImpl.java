package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentEntity;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class ItemRepositoryMapperImpl implements ItemRepositoryMapper{



    @Override
    public Item.ItemComment toItemComment(CommentEntity entity) {

        //160str

        itemId = entityItemId1(entity);
        authorId = entityAuthorId(entity);
        authorName = entityAuthorName(entity);
        id = entity.getId();
        text = entity.getText();
        created = xmlGregorianCalendarToLocalDateTime(dateToXmlGregorianCalendar(entity.getCreated()));
        Item.ItemComment itemComment = new Item.ItemComment(id, text, itemId, authorId, authorName, created);
        return itemComment;
    }

    @Override
    public Comment toComment(CommentEntity entity) {
        if (entity == null) {
            return null;
        }

        LocalDateTime created = null;
        Long id = null;
        String text = null;
        Item item = null;
        User author = null;

        created = toLocalDateTime( entity.getCreated());
        id = entity.getId();
        text = entity.getText();
        item = toItem(entity.getItem());
        author = userRepositoryMapper.toUser(entity.getAuthor());

        Comment comment = new Comment(id, text, item, author, created);

        return comment;
    }

    @Override
    public CommentEntity toEntity(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCreated(toTimestamp(comment.getCreated()));
        commentEntity.setId(comment.getId());
        commentEntity.setText(comment.getText());
    }

}
