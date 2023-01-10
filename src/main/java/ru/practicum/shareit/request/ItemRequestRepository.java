package ru.practicum.shareit.request;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.shareit.user.User;

import java.time.chrono.Chronology;
import java.util.List;

public interface ItemRequestRepository extends PagingAndSortingRepository<ItemRequest, Long> {

    List<ItemRequest> findAllByRequestor(User requestor);
}
