package hanghaeplus.domain.queue.entity;

import hanghaeplus.domain.common.error.CoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static hanghaeplus.domain.queue.entity.enums.QueuePolicy.ACTIVATED_EXPIRED_MINUTE;
import static hanghaeplus.domain.queue.error.QueueErrorCode.EXPIRED_QUEUE_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("QueueToken 단위 테스트")
class QueueTokenUnitTest {

    @Test
    @DisplayName("QueueToken checkExpired 성공 - 토큰 유효")
    void pass_checkExpiredTest() {
        // given
        Long queueTokenId = 1L;
        Long queueId = 1L;
        String tokenId = "UUID";
        LocalDateTime now = LocalDateTime.now().plusMinutes(ACTIVATED_EXPIRED_MINUTE.getMinute()).minusMinutes(1);

        QueueToken sut = QueueToken.createActivated(queueTokenId, queueId, tokenId);

        // when & then
        assertDoesNotThrow(() -> sut.checkExpired(now));
    }

    @Test
    @DisplayName("QueueToken checkExpired 실패 - 토큰 만료")
    void fail_checkExpiredTest() {
        // given
        Long queueTokenId = 1L;
        Long queueId = 1L;
        String tokenId = "UUID";
        LocalDateTime now = LocalDateTime.now().plusMinutes(ACTIVATED_EXPIRED_MINUTE.getMinute()).plusMinutes(1);

        QueueToken queueToken = QueueToken.createActivated(queueTokenId, queueId, tokenId);

        // when
        CoreException sut = assertThrows(CoreException.class, () -> queueToken.checkExpired(now));

        // then
        assertThat(sut.getMessage()).isEqualTo(EXPIRED_QUEUE_TOKEN.getMessage());
    }
}