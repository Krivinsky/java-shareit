package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
	private final UserClient userClient;

	@GetMapping
	public ResponseEntity<Object> getAllUsers() {
		log.info("получен список пользователей ");
		return userClient.getAll();
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody UserDto userDto) {
		log.info("создан пользователь с ID - " + userDto.getId());
		return userClient.createUser(userDto);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Object> getUser(@PathVariable Long userId) {
		log.info("получен пользователь с ID - " + userId);
		return userClient.getById(userId);
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<Object> update(@RequestBody UserDto userDto,
						  @PathVariable Long userId) {
		log.info("обновлен пользователь с ID - " + userDto.getId());
		return userClient.update(userId, userDto);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
		log.info("удален пользователь с ID " + userId);
		return userClient.delete(userId);
	}
}
