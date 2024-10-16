package hanghaeplus.application.queue.service;

import hanghaeplus.domain.queue.dto.QueueCommand;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.entity.enums.QueueTokenStatus;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static hanghaeplus.domain.queue.entity.enums.QueuePolicy.ACTIVATED_EXPIRED_MINUTE;

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
        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(ACTIVATED_EXPIRED_MINUTE.getMinute());
        QueueToken queueToken = QueueToken.createActivated(
                command.queueTokenId(), command.queueId(), command.tokenId(), expiredAt);

        queueTokenRepository.save(queueToken);
    }
}
