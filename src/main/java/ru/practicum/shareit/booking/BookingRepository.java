package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.sql.Timestamp;
import java.util.List;
@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findAllByBookerOrderByStartDesc(User booker);

    @Query("select b from Booking b " +
            "where b.booker = :booker and :now between b.start and b.end order by b.start desc ")
    List<Booking> findCurrentByBooker(@Param("booker") User booker, @Param("now")Timestamp now);

    @Query("select b from Booking b " +
            "where b.booker = :booker and b.end < :now order by b.start desc ")
    List<Booking> findPastByBooker(@Param("booker") User booker, @Param("now") Timestamp now);

    @Query("select b from Booking b " +
            "where b.booker = :booker and b.start > :now order by b.start desc ")
    List<Booking> findFutureByBooker(@Param("booker") User booker, @Param("now") Timestamp now);

    List<Booking> findAllByBookerAndStatusOrderByStartDesc(
            @Param("booker") User booker,
            @Param("status") Status status);

    @Query("select  b from Booking b " +
            "where b.item.owner = :owner order by b.start desc ")
    List<Booking> findAllByByOwnerItems(@Param("owner") User owner);

    @Query("select b from Booking b " +
            " where b.item.owner = :owner and :now between b.start and b.end order by b.start desc ")
    List<Booking> findCurrentByOwnerItems(@Param("owner") User owner, @Param("now") Timestamp now);


    @Query(value = "select b from Booking as b " +
            "where b.booker.id =?1 and b.item.id = ?2 and b.status <> ?3 order by b.start desc")
    List<Booking> findBookingsByBookerAndItemAndStatusNot(Long userId, Long itemId, Status status);

    @Query(value = "select b from Booking as b where b.item.id = ?1 order by b.start asc")
    List<Booking> findBookingsByItemAsc(Long itemId);
}
