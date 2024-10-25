package hanghaeplus.application.queue.service;

import hanghaeplus.application.queue.error.QueueErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueueQueryService {

    private final QueueRepository queueRepository;

    @Transactional(readOnly = true)
    public Queue getQueue(QueueQuery.Create query) {
        return queueRepository.findByConcertId(query.concertId())
                .orElseThrow(() -> new CoreException(QueueErrorCode.NOT_FOUND_CONCERT_QUEUE));
    }

}
