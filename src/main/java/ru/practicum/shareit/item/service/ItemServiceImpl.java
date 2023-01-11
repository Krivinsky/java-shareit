package ru.practicum.shareit.item.service;

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
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;

    private final ItemRequestRepository itemRequestRepository;

    public ItemServiceImpl(UserRepository userRepository, BookingRepository bookingRepository, CommentRepository commentRepository, ItemRepository itemRepository, ItemRequestRepository itemRequestRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
        this.itemRepository = itemRepository;
        this.itemRequestRepository = itemRequestRepository;
    }


    @Override
    public Item creatItem(ItemDto itemDto, Long userId) throws ValidationException, NotFoundException {
        validate(itemDto);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Не найден пользователь с id " + userId);
        }
        User user = optionalUser.get();
        Item item = ItemMapper.toItem(itemDto);
        if (Objects.nonNull(itemDto.getRequestId())) {
            Optional<ItemRequest> optionalItemRequest = itemRequestRepository.findById(itemDto.getRequestId());
            optionalItemRequest.ifPresent(item::setRequest);
        }
        item.setOwner(user);
        itemRepository.save(item);
        return item;
    }

    @Override
    public Item updateItem(ItemDto itemDto, Long userId, Long itemId) throws NotFoundException {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new NotFoundException("Вещь не найдена");
        }
        Item itemForUpdate = itemOptional.get();

        if (!Objects.equals(itemForUpdate.getOwner().getId(), userId)) {
            throw new NotFoundException("Только владелец Item может его обновить");
        }
        if (Objects.nonNull(itemDto.getName())) {
            itemForUpdate.setName(itemDto.getName());
        }
        if (Objects.nonNull(itemDto.getDescription())) {
            itemForUpdate.setDescription(itemDto.getDescription());
        }
        if (Objects.nonNull(itemDto.getAvailable())) {
            itemForUpdate.setAvailable(itemDto.getAvailable());
        }
        itemRepository.save(itemForUpdate);
        return itemForUpdate;
    }

    @Override
    public List<ItemDto> getAll(Long userId) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (userId == 0) {
            return getAllItems();
        } else {
            if (optionalUser.isEmpty()) {
                throw new NotFoundException("Пользователь не найден");
            }
        }
        return getItemsByUser(userId);
    }

    private List<ItemDto> getItemsByUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        return itemRepository.findAllByOwnerOrderById(user)
                .stream()
                .map(ItemMapper::toItemDto)
                .map(this::setLastAndNextBookingForItem)
                .collect(Collectors.toList());

    }

    private List<ItemDto> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getById(Long itemId, Long userId) throws NotFoundException {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new NotFoundException("Предмет не найден");
        }
        ItemDto itemDto = ItemMapper.toItemDto(optionalItem.get());
        itemDto.setComments(commentRepository.findCommentsByItemOrderByCreatedDesc(optionalItem.get())
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList()));
        if (userId.equals(optionalItem.get().getOwner().getId())) {
            return setLastAndNextBookingForItem(itemDto);
        } else {
            return itemDto;
        }
    }

    @Override
    public Item getItem(Long itemId) throws NotFoundException {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new NotFoundException("некорректный ID");
        }
        return optionalItem.get();
    }

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
    public CommentDto creatComment(Long userId, Long itemId, CommentDto commentDto) throws ValidationException, NotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        List<Booking> bookings = bookingRepository
                .findBookingsByBookerAndItemAndStatusNot(userId, itemId, Status.REJECTED);
        if (bookings.isEmpty()) {
            throw new ValidationException("У предмета не было бронирований");
        }
        boolean future = true;
        for (Booking booking : bookings) {
            if (booking.getEnd().isBefore(LocalDateTime.now())) {
                future = false;
                break;
            }
        }
        if (future) {
            throw new ValidationException("Комментарий не может быть оставлен к будущему бронированию");
        }
        Comment comment = CommentMapper.toComment(commentDto);
        comment.setItem(getItem(itemId));
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);
        return CommentMapper.toCommentDto(comment);
    }

    private ItemDto setLastAndNextBookingForItem(ItemDto itemDto) {
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


    private void validate(ItemDto itemDtoRequest) throws ValidationException {
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
