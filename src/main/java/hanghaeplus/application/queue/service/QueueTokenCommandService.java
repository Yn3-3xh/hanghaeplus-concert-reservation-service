package hanghaeplus.application.queue.service;

import hanghaeplus.application.queue.error.QueueErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.queue.dto.QueueCommand;
import hanghaeplus.domain.queue.dto.QueueTokenCommand;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueTokenCommandService {

    private final QueueRepository queueRepository;
    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public void enrollQueueTokenWaiting(QueueCommand.CreateTokenWaiting command) {
        QueueToken queueToken = QueueToken.createWaiting(command.queueId(), command.tokenId());

        queueTokenRepository.save(queueToken);
    }

    public void updateQueueTokenExpired(QueueTokenCommand.CreateQueueTokenExpired command) {
        QueueToken queueToken = queueTokenRepository.findByTokenId(command.tokenId())
                .orElseThrow(() -> new CoreException(QueueErrorCode.NOT_FOUND_QUEUE_TOKEN));
        queueToken.updateExpired();

        queueTokenRepository.save(queueToken);
    }

    // 스케줄러에 사용할 메서드 - 대기열 토큰 만료
    public void refreshExpiredQueue() {
        List<Queue> queues = queueRepository.selectQueues();
        queues.forEach(this::refreshExpiredQueueAsync);
    }

    //    @Async // 정크처리?, 커넥션풀 할당?
    @Transactional
    public void refreshExpiredQueueAsync(Queue queue) {
        // 대기열 토큰 (활성 -> 만료)
        Long queueId = queue.getId();
        List<QueueToken> activatedToExpiredQueueTokens = queueTokenRepository.selectExpiredActiveQueueTokens(queueId).stream()
                .peek(QueueToken::updateExpired).toList();

        // 대기열 토큰 (대기 -> 활성)
        int activatedCount = queueTokenRepository.getActivatedQueueTokenCount(queueId);
        int waitingToActivatedCount = queue.calculateUpdatedRunningCount(activatedToExpiredQueueTokens.size(), activatedCount);

        List<QueueToken> waitingQueueTokens = queueTokenRepository.selectSortedWaitingQueueTokens(queueId, waitingToActivatedCount).stream()
                .peek(QueueToken::updateActivated).toList();

        // 대기열 토큰 저장
        List<QueueToken> queueTokens = new ArrayList<>();
        queueTokens.addAll(activatedToExpiredQueueTokens);
        queueTokens.addAll(waitingQueueTokens);
        queueTokenRepository.saveQueueTokens(queueTokens);
    }
}