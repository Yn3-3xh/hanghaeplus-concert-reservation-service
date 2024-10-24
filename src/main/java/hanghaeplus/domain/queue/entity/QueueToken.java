package hanghaeplus.domain.queue.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.queue.entity.enums.QueueTokenStatus;
import hanghaeplus.domain.queue.error.QueueErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static hanghaeplus.domain.queue.entity.enums.QueuePolicy.ACTIVATED_EXPIRED_MINUTE;
import static hanghaeplus.domain.queue.entity.enums.QueuePolicy.WAITING_EXPIRED_MINUTE;

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

    public void updateActivated() {
        this.status = QueueTokenStatus.ACTIVATED;
    }

    public void updateExpired() {
        this.status = QueueTokenStatus.EXPIRED;
    }

    public void checkExpired(LocalDateTime now) {
        if (this.expiredAt.isBefore(now)) {
            throw new CoreException(QueueErrorCode.EXPIRED_QUEUE_TOKEN);
        }
    }

    public void checkReservation() {
        if (this.status.equals(QueueTokenStatus.WAITING) || this.status.equals(QueueTokenStatus.EXPIRED)) {
            throw new CoreException(QueueErrorCode.NOT_USED_QUEUE_TOKEN);
        }
    }
}
