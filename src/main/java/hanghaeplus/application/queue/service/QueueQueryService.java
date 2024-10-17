package hanghaeplus.application.queue.service;

import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static hanghaeplus.application.queue.error.QueueErrorCode.NOT_FOUND_CONCERT_QUEUE;

@Service
@RequiredArgsConstructor
public class QueueQueryService {

    private final QueueRepository queueRepository;

    @Transactional(readOnly = true)
    public Queue getQueue(QueueQuery.Create query) {
        return queueRepository.findByConcertId(query.concertId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_CONCERT_QUEUE.getMessage()));
    }
}
