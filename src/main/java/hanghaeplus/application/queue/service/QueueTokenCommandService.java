package hanghaeplus.application.queue.service;

import hanghaeplus.domain.queue.dto.QueueCommand;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.entity.enums.QueueTokenStatus;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static hanghaeplus.application.queue.error.QueueErrorCode.NOT_FOUND_QUEUE_TOKEN;

@Service
@RequiredArgsConstructor
public class QueueTokenCommandService {

    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public void enrollQueueTokenWaiting(QueueCommand.CreateTokenWaiting command) {
        QueueToken queueToken = QueueToken.createWaiting(
                command.queueId(), command.tokenId());

        queueTokenRepository.save(queueToken);
    }

    @Transactional
    public void updateQueueTokenActivated(QueueCommand.CreateTokenActivated command) {
        QueueToken queueToken = QueueToken.createActivated(
                command.queueTokenId(), command.queueId(), command.tokenId());

        queueTokenRepository.save(queueToken);
    }

    @Transactional
    public void updateQueueTokenExpired(String tokenId) {
        QueueToken queueToken = queueTokenRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_QUEUE_TOKEN.getMessage()));
        queueToken.updateStatus(QueueTokenStatus.EXPIRED);

        queueTokenRepository.save(queueToken);
    }

    @Async
    @Transactional
    public void updateQueueToken(Long queueId) {
        List<QueueToken> activatedToExpiredQueueTokens = queueTokenRepository.selectExpiredActiveQueueTokens(queueId).stream()
                .map(queueToken -> {
                    queueToken.updateStatus(QueueTokenStatus.EXPIRED);
                    return queueToken;
                }).toList();
        queueTokenRepository.saveQueueTokens(activatedToExpiredQueueTokens);

        int activatedCount = queueTokenRepository.getActivatedQueueTokenCount(queueId);
        int waitingToActivatedCount = activatedToExpiredQueueTokens.size();
        if (activatedCount < 50) {
            waitingToActivatedCount += 50 - activatedCount;
        }
        List<QueueToken> waitingQueueTokens = queueTokenRepository.selectSortedWaitingQueueTokens(queueId, waitingToActivatedCount).stream()
                .map(queueToken -> {
                    queueToken.updateStatus(QueueTokenStatus.ACTIVATED);
                    return queueToken;
                }).toList();
        queueTokenRepository.saveQueueTokens(waitingQueueTokens);
    }
}
