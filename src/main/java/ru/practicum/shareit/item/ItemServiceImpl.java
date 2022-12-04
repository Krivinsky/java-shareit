package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    public Item creatItem(ItemDtoRequest itemDtoRequest, Long userId) throws NotFoundException, ValidationException {
        return itemRepository.creat(itemDtoRequest, userId);
    }

    @Override
    public Item updateItem(ItemDtoRequest itemDtoRequest, Long userId, Long itemId) throws NotFoundException {
        return itemRepository.update(itemDtoRequest, userId, itemId);
    }

    @Override
    public List<ItemDtoResponse> getAll(Long userId) {
        List<ItemDtoResponse> itemDtoResponses = new ArrayList<>();
        List<Item> items = itemRepository.getAll(userId);
        for (Item item : items) {
            ItemDtoResponse itemDtoResponse = ItemMapper.itemDtoResponse(item);
            itemDtoResponses.add(itemDtoResponse);
        }
        return itemDtoResponses;
    }

    @Override
    public Item getItem(Long itemId) throws NotFoundException {
        return itemRepository.getItem(itemId);
    }

    @Override
    public List<Item> search(String text) {
        return itemRepository.search(text);
    }
}
