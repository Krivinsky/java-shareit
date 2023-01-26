package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResp;

import java.util.List;

public interface ItemRequestService {
    ItemRequest create(long userId, ItemRequestDto itemRequestDto);

    List<ItemRequestDtoResp> getByUser(long userId);

    List<ItemRequestDtoResp> getAll(int from, int size, Long userId);

    ItemRequestDtoResp getItemRequestById(Long requestId, Long userId);
}