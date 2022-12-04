package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDtoResponse creatItem(@RequestHeader ("X-Sharer-User-Id") long userId,
                                     @RequestBody ItemDtoRequest itemDtoRequest) throws NotFoundException, ValidationException {
        Item item = itemService.creatItem(itemDtoRequest, userId);
        log.info("создан Item - " + item.getName());
        return ItemMapper.itemDtoResponse(item);
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResponse updateItem(@RequestHeader ("X-Sharer-User-Id") long userId,
                                      @RequestBody ItemDtoRequest itemDtoRequest,
                                      @PathVariable Long itemId) throws NotFoundException {
        Item item = itemService.updateItem(itemDtoRequest, userId, itemId);
        log.info("обновлен Item - " + item.getName());
        return ItemMapper.itemDtoResponse(item);
    }

    @GetMapping
    public List<ItemDtoResponse> getAll(@RequestHeader ("X-Sharer-User-Id") long userId) {
        List<ItemDtoResponse> list = itemService.getAll(userId);
        log.info("получен список из "+ list.size() + " вещей");
        return list;
    }

    @GetMapping("/{itemId}")
    public ItemDtoResponse getItem(@RequestHeader ("X-Sharer-User-Id") long userId,
                                   @PathVariable Long itemId) throws NotFoundException {
        Item item = itemService.getItem(itemId);
        log.info("получен Item  - " + item.getName());
        return ItemMapper.itemDtoResponse(item);
    }

    @GetMapping("/search")
    public List<ItemDtoResponse> search(@RequestHeader ("X-Sharer-User-Id") long userId,
                                  @RequestParam String text) {
        List <Item> items = itemService.search(text);
        List<ItemDtoResponse> list = new ArrayList<>();
        for (Item i : items) {
            list.add(ItemMapper.itemDtoResponse(i));
        }
        log.info("получен список из " + list.size() + " вещей");
        return list;
    }
}
