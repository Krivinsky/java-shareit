package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingEntity;
import ru.practicum.shareit.item.ItemEntity;
import ru.practicum.shareit.item.mapper.ItemRepositoryMapper;
import ru.practicum.shareit.user.UserEntity;
import ru.practicum.shareit.user.mapper.UserRepositoryMapper;

public class BookingRepositoryMapperImpl implements BookingRepositoryMapper {


    UserRepositoryMapper userRepositoryMapper; //---
    ItemRepositoryMapper itemRepositoryMapper;  //---

    //49strok

    @Override
    public BookingEntity toEntity(Booking booking) {
        BookingEntity bookingEntity = new BookingEntity();

        bookingEntity.setStart(convertToTimestamp(booking.getStart()));
        bookingEntity.setEnd(convertToTimestamp(booking.getEnd()));
        bookingEntity.setId(booking.getId());
        bookingEntity.setBooker(userRepositoryMapper.toEntity(booking.getBooker()));
        bookingEntity.setItem(itemRepositoryMapper.toEntity(booking.getItem()));
        bookingEntity.setStatus(booking.getStatus());

        return bookingEntity;
    }

    @Override
    public void updateEntity(Booking booking, BookingEntity entity) {
        if (booking == null ) {
            return;
        }
        if (booking.getStart() != null) {
            entity.setStart(convertToTimestamp(booking.getStart()));
        }
        if (booking.getEnd() != null) {
            entity.setEnd(convertToTimestamp(booking.getEnd()));
        }
        if (booking.getBooker() != null) {
            if (entity.getBooker() == null) {
                entity.setBooker(new UserEntity());
            }
            userRepositoryMapper.updateEntity(booking.getBooker(), entity.getBooker());
        }
        if (booking.getItem() != null) {
            if (entity.getItem() == null) {
                entity.setItem(new ItemEntity());
            }
            itemRepositoryMapper.updateEntity(booking.getItem(), entity.getItem());
        }
    }
}
