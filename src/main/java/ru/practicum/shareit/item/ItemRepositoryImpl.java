package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepositoryImpl;

import java.util.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository{

    Map<Long, Item> items = new HashMap<>();

    protected static Long generateId = 0L;

    protected static Long generateId() {
        return ++generateId;
    }
    @Override
    public Item creat(ItemDtoRequest itemDtoRequest, Long userId) throws NotFoundException, ValidationException {
        validate(itemDtoRequest);
        User user = UserRepositoryImpl.getUserIn(userId);
        Item item = ItemMapper.toItem(itemDtoRequest, userId);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public List<Item> getAll(Long userId) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : items.values()) {
            if (Objects.equals(item.getOwner().getId(), userId)) {
                itemList.add(item);
            }
        }
        return itemList;
    }

    @Override
    public Item update(ItemDtoRequest itemDtoRequest, Long userId, Long itemId) throws NotFoundException {
        Item itemForUpdate = items.get(itemId);

        if (!Objects.equals(itemForUpdate.getOwner().getId(), userId)) {
            throw new NotFoundException("Только владелец Item может его обновить");
        }

        if (Objects.nonNull(itemDtoRequest.getName())) {
            itemForUpdate.setName(itemDtoRequest.getName());
        }
        if (Objects.nonNull(itemDtoRequest.getDescription())) {
            itemForUpdate.setDescription(itemDtoRequest.getDescription());
        }
        if (Objects.nonNull(itemDtoRequest.getAvailable())) {
            itemForUpdate.setAvailable(itemDtoRequest.getAvailable());
        }
        items.put(itemForUpdate.getId(), itemForUpdate);
        return itemForUpdate;
    }

    @Override
    public Item getItem(Long itemId) throws NotFoundException {
        if (Objects.isNull(itemId) || itemId <= 0 ){
            throw new NotFoundException("некорректный ID");
        }
        return items.get(itemId);
    }

    @Override
    public List<Item> search(String text) {
        List<Item> itemList = new ArrayList<>();
        if (text.isEmpty()) {
            return itemList;
        }
        for (Item i : items.values()) {
            if (i.getName().toLowerCase().contains(text.toLowerCase())
                || i.getDescription().toLowerCase().contains(text.toLowerCase())
                && i.getAvailable()) {
                itemList.add(i);
            }
        }
        return itemList;
    }

    private void validate(ItemDtoRequest itemDtoRequest) throws ValidationException {
        if (Objects.isNull(itemDtoRequest.getAvailable())){
            throw new ValidationException("Ошибка в доступности Item");
        }
        if (!itemDtoRequest.getAvailable()) {
            throw new ValidationException("Item не доступен");
        }

        if (itemDtoRequest.getName().isEmpty()) {
            throw new ValidationException("не допустимое имя Item");
        }
        if (Objects.isNull(itemDtoRequest.getDescription()) || itemDtoRequest.getDescription().isEmpty()) {
            throw new ValidationException("не допустимое описание Item");
        }
    }
}
