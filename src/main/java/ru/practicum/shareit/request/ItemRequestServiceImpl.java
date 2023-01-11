package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResp;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemRequestServiceImpl implements ItemRequestService{

    private final ItemRequestRepository itemRequestRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.itemRequestRepository = itemRequestRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemRequest create(long userId, ItemRequestDto itemRequestDto) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id " + userId);
        }
        if (Objects.isNull(itemRequestDto.getDescription())) {
            throw new ValidationException("Описание запроса не может быть пустым");
        }

        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto);
        itemRequest.setRequestor(user.get());
        itemRequest.setCreated(LocalDateTime.now());

        System.out.println(itemRequest);
        itemRequestRepository.save(itemRequest);
        return itemRequest;
    }

    @Override
    public List<ItemRequestDtoResp> getByUser(long userId) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id " + userId);
        }

        List<ItemRequest> itemRequests =
                itemRequestRepository.findAllByRequestorIdOrderByCreatedAsc(userId);

        List<ItemRequestDtoResp> itemRequestDtoResps = itemRequests
                .stream()
                .map(ItemRequestMapper::toItemRequestDtoResp)
                .collect(Collectors.toList());
        itemRequestDtoResps.forEach(this::setItems);
        return itemRequestDtoResps;
    }

    @Override
    public List<ItemRequestDtoResp> getAll(Long from, Long size, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id " + userId);
        }
        List<ItemRequestDtoResp> requestPage = itemRequestRepository.
                findAllByRequestorNotLikeOrderByCreatedAsc(user.get(), PageRequest.of(from.intValue(), size.intValue()))
                .stream()
                .map(ItemRequestMapper::toItemRequestDtoResp)
                .collect(Collectors.toList());
        requestPage.forEach(this::setItems);
        return requestPage;
    }

    @Override
    public ItemRequestDtoResp getItemRequestById(Long requestId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id " + userId);
        }
        Optional<ItemRequest> itemRequest = itemRequestRepository.findById(requestId);
        if (itemRequest.isEmpty()) {
            throw new NotFoundException("Не найден запрос с id " + requestId);
        }
        ItemRequestDtoResp itemRequestDtoResp = ItemRequestMapper.toItemRequestDtoResp(itemRequest.get());
        setItems(itemRequestDtoResp);
        return itemRequestDtoResp;
    }

    private void setItems(ItemRequestDtoResp itemRequestDtoResp) {
        itemRequestDtoResp.setItems(itemRepository.findAllByRequestId(itemRequestDtoResp.getId())
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList()));
    }
}
