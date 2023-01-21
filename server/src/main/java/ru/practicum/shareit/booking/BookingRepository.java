package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "select b from Booking as b " +
            "where b.booker.id =?1 and b.item.id = ?2 and b.status <> ?3 order by b.start desc")
    List<Booking> findBookingsByBookerAndItemAndStatusNot(Long userId, Long itemId, Status status);

    @Query(value = "select b from Booking as b where b.item.id = ?1 order by b.start asc")
    List<Booking> findBookingsByItemAsc(Long itemId);

    List<Booking> findAllByBooker(User booker, Pageable pageable);

    List<Booking> findAllByBookerAndStartBeforeAndEndAfter(User booker, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByBookerAndEndBefore(User booker, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByBookerAndStartAfter(User booker, LocalDateTime start, Pageable pageable);

    List<Booking> findAllByBookerAndStatusEquals(User booker, Status status, Pageable pageable);

    List<Booking> findAllByItemOwner(User owner, Pageable pageable);

    List<Booking> findAllByItemOwnerAndStartBeforeAndEndAfter(User owner, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByItemOwnerAndEndBefore(User owner, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByItemOwnerAndStartAfter(User owner, LocalDateTime start, Pageable pageable);

    List<Booking> findAllByItemOwnerAndStatusEquals(User owner, Status status, Pageable pageable);
}
