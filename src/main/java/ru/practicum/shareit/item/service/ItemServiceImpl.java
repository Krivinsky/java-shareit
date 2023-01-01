package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.dto.ItemDtoResponse;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;

    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;

    @Override
    public Item creatItem(ItemDtoRequest itemDtoRequest, User user) throws ValidationException, NotFoundException {
        validate(itemDtoRequest);
        if (userService.getById(user.getId()) == null) {
            throw new NotFoundException("такого пользователя нет");
        }
        Item item = ItemMapper.toItem(itemDtoRequest, user);
        itemRepository.save(item);
        return item;
    }

    @Override
    public Item updateItem(ItemDtoRequest itemDtoRequest, User user, Long itemId) throws NotFoundException {

        Item itemForUpdate = ItemMapper.toItem(itemDtoRequest, user);
        //возможно проверить есть ли предмет у данного пользователя
        if (!Objects.equals(itemForUpdate.getOwner().getId(), user.getId())) {
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
        itemRepository.save(itemForUpdate);
        return itemForUpdate;
    }

    @Override
    public List<ItemDtoResponse> getAll(Long userId) throws NotFoundException {

        if (userId == 0) {
            return getAllItems();
        } else {
            if (userService.getById(userId) == null) {
                throw new NotFoundException("Пользователь не найден");
            }
        }
        return getItemByUser(userId);
    }

    private List<ItemDtoResponse> getItemByUser(Long userId) throws NotFoundException {
        User user = userService.getById(userId);
        return itemRepository.findAll()
                .stream()
                .filter(item -> item.getOwner() == user)
                .map(ItemMapper::itemDtoResponse)
                .collect(Collectors.toList());
    }

    private List<ItemDtoResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemMapper::itemDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDtoResponse getItemComment(Long itemId, Long userId) throws NotFoundException {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new NotFoundException("Предмет не найден");
        }
        ItemDtoResponse item = ItemMapper.itemDtoResponse(optionalItem.get());
        item.setComments(commentRepository.findCommentsByItemOrderByCreatedDesc(optionalItem.get())
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList()));
        if (userId.equals(optionalItem.get().getOwner().getId())) {
            return setLastAndNextBookingForItem(item);
        } else {
            return item;
        }

    }

    @Override
    public Item getItem(Long itemId) throws NotFoundException {
        if (Objects.isNull(itemId) || itemId <= 0) {
            throw new NotFoundException("некорректный ID");
        }
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new NotFoundException("Предмет не найден");
        }
        return optionalItem.get();
    }

    //todo метод с комметнтариями

    @Override
    public List<Item> search(String text) {

        List<Item> itemList = new ArrayList<>();
        if (text.isEmpty()) {
            return itemList;
        }
        List<Item> findByName = itemRepository.findItemByNameContainsIgnoreCase(text);
        List<Item> findByDescription = itemRepository.findItemByDescriptionContainsIgnoreCase(text);
        itemList = Stream.concat(findByDescription.stream(), findByName.stream())
                .distinct()
                .filter(Item::getAvailable)
                .collect(Collectors.toList());
        return itemList;
    }

    @Override
    public CommentDto creatComment(Long userId, Long itemId, CommentDto commentDto) throws NotFoundException {
        List<Booking> bookings = bookingRepository
                .findBookingsByBookerAndItemAndStatusNot(userId, itemId, Status.REJECTED);
        if (bookings.isEmpty()) {
            throw new IllegalStateException("У предмета не было бронирований");
        }
        boolean future = true;
        for (Booking booking : bookings) {
            if (booking.getEnd().isBefore(LocalDateTime.now())) {
                future = false;
                break;
            }
        }
        if (future) {
            throw new IllegalStateException("Комментарий не может быть оставлен к будущему бронированию");
        }
        Comment comment = CommentMapper.toComment(commentDto);
        comment.setItem(getItem(itemId));
        comment.setAuthor(userService.getById(userId));
        comment.setCreated(LocalDateTime.now());
        return null;
    }
    private ItemDtoResponse setLastAndNextBookingForItem(ItemDtoResponse itemDto) {
        Booking lastBooking = null;
        Booking nextBooking = null;
        List<Booking> bookings = bookingRepository.findBookingsByItemAsc(itemDto.getId());
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            if (booking.getStart().isAfter(LocalDateTime.now())) {
                nextBooking = booking;
                if (i != 0) {
                    lastBooking = bookings.get(i - 1);
                }
                break;
            }
        }
        if (lastBooking != null) {
            itemDto.setLastBooking(BookingMapper.toBookingDto(lastBooking));
        }
        if (nextBooking != null) {
            itemDto.setNextBooking(BookingMapper.toBookingDto(nextBooking));
        }
        return itemDto;
    }


    private void validate(ItemDtoRequest itemDtoRequest) throws ValidationException {
        if (Objects.isNull(itemDtoRequest.getAvailable())) {
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
