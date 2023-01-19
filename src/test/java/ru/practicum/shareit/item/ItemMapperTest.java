package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest({ItemMapper.class})
class ItemMapperTest {

    private ItemMapper itemMapper;

    @Test
    void toItem() {
        ItemDto itemDtoOriginal = ItemDto.builder()
                .id(1L)
                .name("ItemName")
                .description("ItemDescription")
                .available(true)
                .build();

        Item itemResult = ItemMapper.toItem(itemDtoOriginal);
        assertNotNull(itemResult);
        assertEquals(itemDtoOriginal.getId(), itemResult.getId());
        assertEquals(itemDtoOriginal.getName(), itemResult.getName());
        assertEquals(itemDtoOriginal.getDescription(), itemResult.getDescription());
    }

    @Test
    void toItemDto() {
        Item itemOriginal = Item.builder()
                .id(1L)
                .name("ItemName")
                .description("ItemDescription")
                .available(true)
                .build();

        ItemDto itemDtoResult = ItemMapper.toItemDto(itemOriginal);
        assertNotNull(itemDtoResult);
        assertEquals(itemDtoResult.getId(), itemOriginal.getId());
        assertEquals(itemDtoResult.getName(), itemOriginal.getName());
        assertEquals(itemDtoResult.getDescription(), itemOriginal.getDescription());
    }

    @Test
    void toItemShortDtoTest() {
        Item itemOriginal = Item.builder()
                .id(1L)
                .name("ItemName")
                .owner(User.builder().id(1L).build())
                .description("ItemDescription")
                .available(true)
                .request(ItemRequest.builder().id(1L).build())
                .build();

        ItemShortDto itemShortDtoResult = ItemMapper.toItemShortDto(itemOriginal);
        assertNotNull(itemShortDtoResult);
        assertEquals(itemShortDtoResult.getId(), itemOriginal.getId());
        assertEquals(itemShortDtoResult.getName(), itemOriginal.getName());
        assertEquals(itemShortDtoResult.getDescription(), itemOriginal.getDescription());
        assertEquals(itemShortDtoResult.getOwnerId(), itemOriginal.getOwner().getId());
        assertEquals(itemShortDtoResult.getAvailable(), itemOriginal.getAvailable());
        assertEquals(itemShortDtoResult.getRequestId(), itemOriginal.getRequest().getId());
    }
}