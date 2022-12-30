package ru.practicum.shareit.item.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findItemByNameContainsIgnoreCase(String name);

    List<Item> findItemByDescriptionContainsIgnoreCase(String description);
}