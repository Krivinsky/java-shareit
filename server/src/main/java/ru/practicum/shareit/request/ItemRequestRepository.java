package ru.practicum.shareit.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface ItemRequestRepository extends PagingAndSortingRepository<ItemRequest, Long> {

    List<ItemRequest> findAllByRequestorIdOrderByCreatedAsc(Long userId);

    List<ItemRequest> findAllByRequestorNotLikeOrderByCreatedAsc(User user, Pageable pageable);
}
