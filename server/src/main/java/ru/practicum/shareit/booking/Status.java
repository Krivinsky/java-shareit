package ru.practicum.shareit.booking;

import java.util.Optional;

public enum Status {
    WAITING, //новое бронирование, ожидает одобрения
    APPROVED, //бронирование подтверждено владельцем
    REJECTED, //бронирование отклонено владельцем
    CANCELED;  //бронирование отменено создателем

    public static Optional<Status> from(String stringState) {
        for (Status state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
