package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResp;

import java.util.List;

public interface ItemRequestService {
    ItemRequest create(long userId, ItemRequestDto itemRequestDto);

    List<ItemRequestDtoResp> get(long userId);

    List<ItemRequest> getAll(Long from, Long size);

    ItemRequest getItemRequestById(Long requestId);
}