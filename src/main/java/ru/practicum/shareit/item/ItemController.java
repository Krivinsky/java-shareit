package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/items")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto creatItem(@RequestHeader ("X-Sharer-User-Id") long userId,
                             @RequestBody ItemDto itemDto) throws NotFoundException, ValidationException {
        Item item = itemService.creatItem(itemDto, userId);
        log.info("создан Item - " + item.getName());
        return ItemMapper.itemDto(item);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader ("X-Sharer-User-Id") long userId,
                                      @RequestBody ItemDto itemDto,
                                      @PathVariable Long itemId) throws NotFoundException {
        Item item = itemService.updateItem(itemDto, userId, itemId);
        log.info("обновлен Item - " + item.getName());
        return ItemMapper.itemDto(item);
    }

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader ("X-Sharer-User-Id") long userId) throws NotFoundException {
        List<ItemDto> list = itemService.getAll(userId);
        log.info("получен список из " + list.size() + " вещей");
        return list;
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader ("X-Sharer-User-Id") long userId,
                           @PathVariable Long itemId) throws NotFoundException {
        ItemDto item = itemService.getById(itemId, userId);
        log.info("получен Item  - " + item.getName());
        return item;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                @RequestParam String text) {
        List<Item> items = itemService.search(text);
        List<ItemDto> list = new ArrayList<>();
        for (Item i : items) {
            list.add(ItemMapper.itemDto(i));
        }
        log.info("получен список из " + list.size() + " вещей");
        return list;
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto create(@Min(1L) @PathVariable Long itemId,
                             @RequestHeader ("X-Sharer-User-Id") Long userId,
                             @Valid @RequestBody CommentDto commentDto) throws NotFoundException, ValidationException {
        CommentDto commentDto1 = itemService.creatComment(userId, itemId, commentDto);
        System.out.println(commentDto1);
        return commentDto1;
    }
}
