package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserEntity;
import ru.practicum.shareit.user.mapper.UserRepositoryMapper;

public class UserRepositoryMapperIml implements UserRepositoryMapper {
    @Override
    public User toUser(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        Long id;
        String name;
        String email;

        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();

        User user = new User(id, name, email);

        return user;
    }

    @Override
    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userEntity.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(userEntity.getEmail());

        return userEntity;
    }

    @Override
    public void updateEntity(User user, UserEntity entity) {
        if (user == null) {
            return;
        }

        if (user.getName() != null) {
            entity.setName(user.getName());
        }
        if (user.getEmail() != null) {
            entity.setEmail(user.getEmail());
        }
    }
}
