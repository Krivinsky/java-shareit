package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
	private final ItemRequestClient itemRequestClient;

	@PostMapping
	public ResponseEntity<Object> creatRequest(@RequestHeader("X-Sharer-User-Id") long userId,
											   @RequestBody @Valid ItemRequestDto itemRequestDto) {
		log.info("Создан запрос на " + itemRequestDto.getDescription());
		return itemRequestClient.creatRequest(userId, itemRequestDto);
	}

	@GetMapping("/all")
	public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
										 @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
										 @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
		log.info("Получены запросы других пользователей от пользователя - " + userId);
		return itemRequestClient.getAll(userId, from, size);
	}

	@GetMapping("/{requestId}")
	public ResponseEntity<Object> getItemRequestById(@RequestHeader("X-Sharer-User-Id") long userId,
													 @PathVariable Long requestId) {
		log.info("Получен запрос с Id - " + requestId);
		return itemRequestClient.getItemRequestById(userId, requestId);
	}

	@GetMapping
	public ResponseEntity<Object> getByUser(@RequestHeader ("X-Sharer-User-Id") long userId,
											@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
											@Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
		return itemRequestClient.getByUser(userId, from, size);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Map<String, String>> errorHandler(IllegalArgumentException ex) {
		Map<String, String> resp = new HashMap<>();
		resp.put("error", String.format("Unknown state: UNSUPPORTED_STATUS"));
		return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
	}
}
