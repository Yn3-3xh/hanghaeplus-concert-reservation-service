package hanghaeplus.application.queue.service;

import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueueTokenQueryService {

    private final QueueTokenRepository queueTokenRepository;

    @Transactional(readOnly = true)
    public int getWaitingPosition(QueueQuery.CreateTokenWaiting query) {
        return queueTokenRepository.getWaitingPosition(query.tokenId(), query.queueId());
    }
}
