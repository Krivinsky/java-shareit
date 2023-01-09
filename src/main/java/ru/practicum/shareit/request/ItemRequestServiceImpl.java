package ru.practicum.shareit.request;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.exeption.ErrorResponse;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemRequestServiceImpl implements ItemRequestService{

    private final ItemRequestRepository itemRequestRepository;

    private final UserRepository userRepository;

    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, UserRepository userRepository) {
        this.itemRequestRepository = itemRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ItemRequest create(long userId, ItemRequestDto itemRequestDto) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("не найден пользователь с id " + userId);
        }
        if (Objects.isNull(itemRequestDto.getDescription())) {
            throw new ValidationException("Описание запроса не может быть пустым");
        }
        ItemRequest itemRequest = new ItemRequest();
        System.out.println(itemRequest);
        itemRequestRepository.save(itemRequest);
        return itemRequest;
    }

    @Override
    public List<ItemRequest> get(long userId) {
        return null;
    }

    @Override
    public List<ItemRequest> getAll(Long from, Long size) {
        return null;
    }

    @Override
    public ItemRequest getItemRequestById(Long requestId) {
        return null;
    }


}
