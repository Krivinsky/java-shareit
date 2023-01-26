package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
	private final ItemClient itemClient;

	@PostMapping
	public ResponseEntity<Object> creatItem(@RequestHeader ("X-Sharer-User-Id") Long userId,
										 @Valid @RequestBody ItemDto itemDto) {
		log.info("создан Item - " + itemDto.getName());
		return itemClient.createItem(itemDto, userId);
	}

	@PatchMapping("/{itemId}")
	public ResponseEntity<Object> updateItem(@RequestHeader ("X-Sharer-User-Id") Long userId,
											 @RequestBody ItemDto itemDto,
											 @PathVariable Long itemId) {
		log.info("обновлен Item с ID - " + itemDto.getId());
		return itemClient.update(userId, itemDto, itemId);
	}

	@GetMapping
	public ResponseEntity<Object> getAllItems(@RequestHeader ("X-Sharer-User-Id") Long userId,
											  @PositiveOrZero @RequestParam (value = "from", required = false, defaultValue = "0") Integer from,
											  @Positive @RequestParam (value = "size", required = false, defaultValue = "10") Integer size) {
		log.info("получен список пользователей ");
		return itemClient.getAll(userId, from, size);
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<Object> getItem(@RequestHeader ("X-Sharer-User-Id") Long userId,
										  @PathVariable Long itemId) {
		log.info("получен Item  - " + itemId);
		return itemClient.getById(itemId, userId);
	}

	@GetMapping("/search")
	public ResponseEntity<Object> search(@RequestHeader ("X-Sharer-User-Id") Long userId,
										@RequestParam String text,
										@Min(0) @RequestParam (value = "from", required = false, defaultValue = "0") Long from,
										@Min (1) @RequestParam (value = "size", required = false, defaultValue = "10") Long size) {
		return itemClient.search(userId, from, size, text);
	}

	@PostMapping("/{itemId}/comment")
	public ResponseEntity<Object> createComment(@Min(1L) @PathVariable Long itemId,
									@RequestHeader ("X-Sharer-User-Id") Long userId,
									@Valid @RequestBody CommentDto commentDto)  {
		return itemClient.createComment(commentDto, itemId, userId);
	}


	@DeleteMapping("/{itemId}")
	public ResponseEntity<Object> deleteItem(@PathVariable Long itemId) {
		log.info("удален пользователь с ID " + itemId);
		return itemClient.delete(itemId);
	}
}
