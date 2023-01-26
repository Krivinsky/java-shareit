package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.exeption.ErrorResponse;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    public ItemDto creatItem(@RequestHeader ("X-Sharer-User-Id") Long userId,
                             @RequestBody ItemDto itemDto) throws NotFoundException, ValidationException {
        ItemDto itemDto1 = itemService.creatItem(itemDto, userId);
        log.info("создан Item - " + itemDto1.getName());
        return itemDto1;
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader ("X-Sharer-User-Id") Long userId,
                              @RequestBody ItemDto itemDto,
                              @PathVariable Long itemId) throws NotFoundException {
        ItemDto item = itemService.updateItem(itemDto, userId, itemId);
        log.info("обновлен Item - " + item.getName());
        return item;
    }

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                @RequestParam (value = "from", required = false, defaultValue = "0") int from,
                                @RequestParam (value = "size", required = false, defaultValue = "10") int size) throws NotFoundException {
        List<ItemDto> list = itemService.getAll(userId, from, size);
        log.info("получен список из " + list.size() + " вещей");
        return list;
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader ("X-Sharer-User-Id") Long userId,
                           @PathVariable Long itemId) throws NotFoundException {
        ItemDto item = itemService.getById(itemId, userId);
        log.info("получен Item  - " + item.getName());
        return item;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                @RequestParam String text,
                                @Min (0) @RequestParam (value = "from", required = false, defaultValue = "0") Long from,
                                @Min (1) @RequestParam (value = "size", required = false, defaultValue = "10") Long size) {
        List<ItemDto> items = itemService.search(text,  from, size);
        log.info("получен список из " + items.size() + " вещей");
        return items;
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@Min(1L) @PathVariable Long itemId,
                             @RequestHeader ("X-Sharer-User-Id") Long userId,
                             @Valid @RequestBody CommentDto commentDto) throws NotFoundException, ValidationException {
        CommentDto commentDto1 = itemService.creatComment(userId, itemId, commentDto);
        System.out.println(commentDto1);
        return commentDto1;
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) throws NotFoundException {
        log.info("удалена вещь с ID " + itemId);
        itemService.delete(itemId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectParameterException(ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }
}
