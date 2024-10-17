package hanghaeplus.domain.queue.entity;

import hanghaeplus.domain.queue.entity.enums.QueueTokenStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static hanghaeplus.domain.queue.error.QueueErrorCode.EXPIRED_QUEUE_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QueueToken 단위 테스트")
class QueueTokenUnitTest {

    @Test
    @DisplayName("QueueToken checkExpired 성공 - 토큰 유효")
    void pass_checkExpiredTest() {
        // given
        Long queueTokenId = 1L;
        Long queueId = 1L;
        String tokenId = "UUID";
        QueueTokenStatus status = QueueTokenStatus.ACTIVATED;
        LocalDateTime expiredAt = LocalDateTime.of(2024, 10, 18, 11, 0);

        QueueToken queueToken = new QueueToken(queueTokenId, queueId, tokenId, status, expiredAt);

        // when & then
        assertDoesNotThrow(queueToken::checkExpired);
    }

    @Test
    @DisplayName("QueueToken checkExpired 실패 - 토큰 만료")
    void fail_checkExpiredTest() {
        // given
        Long queueTokenId = 1L;
        Long queueId = 1L;
        String tokenId = "UUID";
        QueueTokenStatus status = QueueTokenStatus.EXPIRED;
        LocalDateTime expiredAt = LocalDateTime.of(2024, 10, 17, 11, 0);

        QueueToken queueToken = new QueueToken(queueTokenId, queueId, tokenId, status, expiredAt);

        // when
        IllegalStateException exception = assertThrows(IllegalStateException.class, queueToken::checkExpired);

        // then
        assertThat(exception.getMessage()).isEqualTo(EXPIRED_QUEUE_TOKEN.getMessage());
    }
}