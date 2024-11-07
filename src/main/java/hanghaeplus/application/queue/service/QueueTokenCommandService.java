package hanghaeplus.application.queue.service;

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

import java.util.List;

import static hanghaeplus.application.queue.error.QueueErrorCode.NOT_FOUND_POP_WAITING_QUEUE_TOKEN;

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

    public void deleteQueueToken(QueueTokenCommand.CreateQueueTokenDelete command) {
        queueTokenRepository.deleteQueueToken(command.queueId(), command.tokenId());
    }

    // 스케줄러에 사용할 메서드 - 대기열 토큰 만료
//    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void refreshExpiredQueue() {
        List<Queue> queues = queueRepository.selectQueues();
        queues.forEach(this::refreshExpiredQueueAsync);
    }

    //    @Async // 정크처리?, 커넥션풀 할당?
    @Transactional
    public void refreshExpiredQueueAsync(Queue queue) {
        // 대기열 토큰 (활성 -> 만료)
        /* RDBMS -> Redis: 만료는 TTL 기능으로 대체
        Long queueId = queue.getId();
        List<QueueToken> activatedToExpiredQueueTokens = queueTokenRepository.selectExpiredActiveQueueTokens(queueId).stream()
                .peek(QueueToken::updateExpired).toList();
        */

        // 대기열 토큰 (대기 -> 활성)
        Long queueId = queue.getId();
//        List<QueueToken> activatedToExpiredQueueTokens = queueTokenRepository.selectExpiredActiveQueueTokens(queueId);
        int currentActiveCount = queueTokenRepository.getExpiredActiveQueueTokenCount(queueId).orElse(0);
        int waitingToActivatedCount = queue.getRunningLimitedCount() - currentActiveCount;

//        int waitingToActivatedCount = queue.calculateUpdatedRunningCount(expiredActiveCount, activatedCount);
//        List<QueueToken> waitingQueueTokens = queueTokenRepository.selectSortedWaitingQueueTokens(queueId, waitingToActivatedCount).stream()
//                .peek(QueueToken::updateActivated).toList();

        List<QueueToken> queueTokens = queueTokenRepository.popWaitingQueueToken(queueId, waitingToActivatedCount);
        if (queueTokens == null || queueTokens.isEmpty()) {
            throw new CoreException(NOT_FOUND_POP_WAITING_QUEUE_TOKEN);
        }
        queueTokenRepository.insertActivatedQueueTokens(queueTokens);

        // 대기열 토큰 저장
//        List<QueueToken> queueTokens = new ArrayList<>();
//        queueTokens.addAll(activatedToExpiredQueueTokens);
//        queueTokens.addAll(waitingQueueTokens);
//        queueTokenRepository.saveQueueTokens(queueTokens);
    }

}