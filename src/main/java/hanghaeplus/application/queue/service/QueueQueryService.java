package hanghaeplus.application.queue.service;

import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class QueueQueryService {

    private final QueueRepository queueRepository;

    @Transactional(readOnly = true)
    public Queue getQueue(QueueQuery.Create query) {
        return queueRepository.findByConcertId(query.concertId())
                .orElseThrow(() -> new NoSuchElementException("콘서트의 대기열이 존재하지 않습니다."));
    }
}
