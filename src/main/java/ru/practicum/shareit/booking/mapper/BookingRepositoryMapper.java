package ru.practicum.shareit.booking.mapper;

import org.mapstruct.*;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingEntity;
import ru.practicum.shareit.item.mapper.ItemRepositoryMapper;
import ru.practicum.shareit.user.mapper.UserRepositoryMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        uses = {UserRepositoryMapper.class,
                ItemRepositoryMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingRepositoryMapper {

    @Mapping(target = "start", source = "start", qualifiedByName = "convertToLocalDateTime")
    @Mapping(target = "end", source = "end", qualifiedByName = "convertToLocalDateTime")
    Booking toBooking(BookingEntity entity);

    @Mapping(target = "start", source = "start", qualifiedByName = "convertToTimestamp")
    @Mapping(target = "end", source = "end", qualifiedByName = "convertToTimestamp")
    BookingEntity toEntity(Booking booking);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "start", source = "start", qualifiedByName = "convertToTimestamp")
    @Mapping(target = "end", source = "end", qualifiedByName = "convertToTimestamp")
    void updateEntity(Booking booking, @MappingTarget BookingEntity entity);

    @Named("convertToTimestamp")
    default Timestamp convertToTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    @Named("convertToLocalDateTime")
    default LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
