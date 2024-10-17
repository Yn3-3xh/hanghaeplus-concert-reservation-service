package hanghaeplus.application.queue.service;

import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static hanghaeplus.application.queue.error.QueueErrorCode.NOT_FOUND_QUEUE_TOKEN;

@Service
@RequiredArgsConstructor
public class QueueTokenQueryService {

    private final QueueTokenRepository queueTokenRepository;

    @Transactional(readOnly = true)
    public int getWaitingPosition(QueueQuery.CreateTokenWaiting query) {
        return queueTokenRepository.getWaitingPosition(query.tokenId(), query.queueId());
    }

    public QueueToken getQueueToken(QueueQuery.CreateToken query) {
        return queueTokenRepository.findByTokenId(query.tokenId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_QUEUE_TOKEN.getMessage()));
    }
}
