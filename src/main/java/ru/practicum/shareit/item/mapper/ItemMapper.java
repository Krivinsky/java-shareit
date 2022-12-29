package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;
import ru.practicum.shareit.user.User;

import java.util.Objects;

public class ItemMapper {

    public static Item toItem(ItemDtoRequest itemDtoRequest, User user) {
        if (Objects.isNull(itemDtoRequest.getId())) {
            return new Item(
                    ItemRepositoryImpl.generateId(),
                    itemDtoRequest.getName(),
                    itemDtoRequest.getDescription(),
                    itemDtoRequest.getAvailable(),
                    user,
                    null
            );
        } else {
            return new Item(
                    itemDtoRequest.getId(),
                    itemDtoRequest.getName(),
                    itemDtoRequest.getDescription(),
                    itemDtoRequest.getAvailable(),
                    user,
                    null
            );
        }
    }

    public static ItemDtoResponse itemDtoResponse(Item item) {
        return new ItemDtoResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    //
//    @Mapping(target = "owner.id", source = "userId")
//    @Mapping(target = "lastBooking", ignore = true)
//    @Mapping(target = "nextBooking", ignore = true)
//    Item toItem(ItemCreateRequest request, Long userId);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "request", ignore = true)
//    @Mapping(target = "owner.id", source = "userId")
//    @Mapping(target = "lastBooking", ignore = true)
//    @Mapping(target = "nextBooking", ignore = true)
//    Item toItem(ItemUpdateRequest request, Long userId);
//
//    ItemResponse toItemResponse(Item item);
//
//    ItemDtoResponse.ItemBookingResponse map(Item.ItemBooking booking);
//
//    ItemDtoResponse.ItemBookingResponse map(Item.ItemComment comment);
//
//    @Mapping(target = "itemId", source = "item.Id")
//    @Mapping(target = "authorId", source = "author.Id")
//    @Mapping(target = "authorName", source = "author.name")
//    CommentResponse toResponse(Comment comment);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "created", ignore = true)
//    @Mapping(target = "text", source = "request.text")
//    @Mapping(target = "item.id", source = "itemId")
//    @Mapping(target = "author.id", source = "authorId")
//    Comment toComment(CommentCreatRequest request, Long itemId, Long authorId);
}
