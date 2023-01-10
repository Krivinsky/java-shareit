package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.exeption.ErrorResponse;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResp;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.Instant;
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

    //private final Sort sort = Sort.by(Sort.Direction.DESC, "created");

    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, UserRepository userRepository) {
        this.itemRequestRepository = itemRequestRepository;
        this.userRepository = userRepository;
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
    public List<ItemRequestDtoResp> get(long userId) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id " + userId);
        }
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequestor(user.get());
        List<ItemRequestDtoResp> itemRequestDtoResps = itemRequests
                .stream()
                .map(ItemRequestMapper::toItemRequestDtoResp)
                .sorted(Comparator.comparing(ItemRequestDtoResp::getCreated))
                .collect(Collectors.toList());

        return itemRequestDtoResps;
    }

    @Override
    public List<ItemRequest> getAll(Long from, Long size) {

        Page<ItemRequest> requestPAge = itemRequestRepository.findAll(PageRequest.of(from.intValue(), size.intValue()));
        return requestPAge.toList();
    }

    @Override
    public ItemRequest getItemRequestById(Long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Не найден запрос с id " + requestId));
        return itemRequest;
    }


}
