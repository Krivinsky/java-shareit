package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.UserException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
