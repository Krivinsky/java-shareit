package ru.practicum.shareit.comment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest({CommentMapper.class})
class CommentMapperTest {
    private CommentMapper commentMapper;

    @Test
    void toCommentTest() {
        CommentDto commentDtoOriginal = CommentDto.builder()
                .id(1L)
                .authorName("John")
                .created(LocalDateTime.now())
                .build();

        Comment commentResult = CommentMapper.toComment(commentDtoOriginal);
        assertNotNull(commentDtoOriginal);
        assertEquals(commentDtoOriginal.getId(), commentResult.getId());
    }

    @Test
    void toCommentDtoTest() {
        Comment commentOriginal = new Comment();
        commentOriginal.setId(1L);
        commentOriginal.setText("text");
        commentOriginal.setItem(new Item());
        commentOriginal.setAuthor(new User());

        CommentDto commentResult = CommentMapper.toCommentDto(commentOriginal);
        assertNotNull(commentResult);
        assertEquals(commentResult.getId(), commentResult.getId());

    }
}