package hanghaeplus.application.queue.service;

import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hanghaeplus.application.queue.error.QueueErrorCode.*;

@Service
@RequiredArgsConstructor
public class QueueTokenQueryService {

    private final QueueRepository queueRepository;
    private final QueueTokenRepository queueTokenRepository;

    @Transactional(readOnly = true)
    public int getWaitingPosition(QueueQuery.CreateTokenWaiting query) {
        return queueTokenRepository.getWaitingPosition(query.tokenId(), query.queueId());
    }

    public QueueToken getWaitingQueueToken(QueueQuery.CreateWaitingQueueToken createWaitingQueueToken) {
        Queue queue = queueRepository.findByConcertId(createWaitingQueueToken.concertId())
                .orElseThrow(() -> new CoreException(NOT_FOUND_CONCERT_QUEUE));

        return queueTokenRepository.findWaitingQueueToken(queue.getId(), createWaitingQueueToken.tokenId())
                .orElseThrow(() -> new CoreException(NOT_FOUND_WAITING_QUEUE_TOKEN));
    }

    public QueueToken getActiveQueueToken(QueueQuery.CreateActiveQueueToken createActiveQueueToken) {
        Queue queue = queueRepository.findByConcertId(createActiveQueueToken.concertId())
                .orElseThrow(() -> new CoreException(NOT_FOUND_CONCERT_QUEUE));

        return queueTokenRepository.findActiveQueueToken(queue.getId(), createActiveQueueToken.tokenId())
                .orElseThrow(() -> new CoreException(NOT_FOUND_ACTIVE_QUEUE_TOKEN));
    }
}
