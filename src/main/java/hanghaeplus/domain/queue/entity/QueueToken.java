package hanghaeplus.domain.queue.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import hanghaeplus.domain.queue.entity.enums.QueueTokenStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static hanghaeplus.domain.queue.entity.enums.QueuePolicy.ACTIVATED_EXPIRED_MINUTE;
import static hanghaeplus.domain.queue.entity.enums.QueuePolicy.WAITING_EXPIRED_MINUTE;
import static hanghaeplus.domain.queue.error.QueueErrorCode.EXPIRED_QUEUE_TOKEN;

@Entity
@Table(name = "queue_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class QueueToken extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long queueId;

    private String tokenId;

    @Enumerated(EnumType.STRING)
    private QueueTokenStatus status;

    private LocalDateTime expiredAt;

    public static QueueToken createWaiting(Long queueId, String tokenId) {
        return new QueueToken(null, queueId, tokenId, QueueTokenStatus.WAITING,
                LocalDateTime.now().plusMinutes(WAITING_EXPIRED_MINUTE.getMinute()));
    }

    public static QueueToken createActivated(Long id, Long queueId, String tokenId) {
        return new QueueToken(id, queueId, tokenId, QueueTokenStatus.ACTIVATED,
                LocalDateTime.now().plusMinutes(ACTIVATED_EXPIRED_MINUTE.getMinute()));
    }

    public void updateStatus(QueueTokenStatus status) {
        this.status = status;
    }

    public void checkExpired() {
        if (this.expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException(EXPIRED_QUEUE_TOKEN.getMessage());
        }
    }
}
