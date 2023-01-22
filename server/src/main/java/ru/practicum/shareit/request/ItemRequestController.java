package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exeption.ErrorResponse;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoResp;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@Slf4j
@Validated
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ItemRequest create(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = itemRequestService.create(userId, itemRequestDto);
        log.info("Создан запрос на " + itemRequest.getDescription());
        return itemRequest;
    }

    @GetMapping
    public List<ItemRequestDtoResp> getByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        List<ItemRequestDtoResp> itemRequestDtoResps = itemRequestService.getByUser(userId);
        log.info("Получены запросы от пользователя " + userId);
        return itemRequestDtoResps;
    }

    @GetMapping("/all")
    public List<ItemRequestDtoResp> getAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestParam (value = "from", defaultValue = "0") int from,
                                           @RequestParam (value = "size", defaultValue = "1") int size) {
        List<ItemRequestDtoResp> itemRequests = itemRequestService.getAll(from, size, userId);
        log.info("Получены запросы других пользователей в количестве - " + itemRequests.size());
        return itemRequests;
    }

    @GetMapping("/{requestId}")
    public ItemRequestDtoResp getItemRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable Long requestId) {
        ItemRequestDtoResp itemRequestDtoResp = itemRequestService.getItemRequestById(requestId, userId);
        log.info("Получен запрос с Id - " + requestId);
        return itemRequestDtoResp;
    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleNotFoundException(NotFoundException e) {
//        return new ErrorResponse(e.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleIncorrectParameterException(ValidationException e) {
//        return new ErrorResponse(e.getMessage());
//    }
}
