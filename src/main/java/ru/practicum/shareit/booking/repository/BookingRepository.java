package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.Status;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingEntity;
import ru.practicum.shareit.user.UserEntity;

import java.sql.Timestamp;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findAllByBookerOrderByStartDesc(UserEntity booker);

    @Query("select b from BookingEntity b " +
            "where b.booker = :booker and :now between b.start and b.end order by b.start desc ")
    List<BookingEntity> findCurrentByBooker(@Param("booker") UserEntity booker, @Param("now")Timestamp now);

    @Query("select b from BookingEntity b " +
            "where b.booker = :booker and b.end < :now order by b.start desc ")
    List<BookingEntity> findPastByBooker(@Param("booker") UserEntity booker, @Param("now") Timestamp now);

    @Query("select b from BookingEntity b " +
            "where b.booker = :booker and b.start > :now order by b.start desc ")
    List<BookingEntity> findFutureByBooker(@Param("booker") UserEntity booker, @Param("now") Timestamp now);

    List<BookingEntity> findAllByBookerAndStatusOrderByStartDesc(
            @Param("booker") UserEntity booker,
            @Param("status") Status status);

    @Query("select  b from BookingEntity b " +
            "where b.item.owner = :owner order by b.start desc ")
    List<BookingEntity> findAllByByOwnerItems(@Param("owner") UserEntity owner);

    @Query("select b from BookingEntity b " +
            " where b.item.owner = :owner and :now between b.start and b.end order by b.start desc ")
    List<BookingEntity> findCurrentByOwnerItems(@Param("owner") UserEntity owner, @Param("now") Timestamp now);

    Booking creatBooking(Booking booking, Long userId);

    Booking updateBooking(Booking booking);

    List<Booking> getAll(Long userId);

    Booking getBooking();
}
