package hanghaeplus.domain.queue.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.queue.error.QueueErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QueueQuery 단위 테스트")
class QueueQueryUnitTest {

    @Nested
    @DisplayName("QueueQuery.Create 테스트")
    class CreateQueueQueryTests {
        @Test
        @DisplayName("QueueQuery.Create 성공")
        void pass_createQueueQueryTest() {
            // given
            Long concertId = 1L;

            // when
            QueueQuery.Create query = new QueueQuery.Create(concertId);

            // then
            assertNotNull(query);
        }

        @Test
        @DisplayName("QueueQuery.Create 실패 - concertId가 null인 경우")
        void fail_createQueueQueryTest1() {
            // given
            Long concertId = null;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueQuery.Create(concertId);
            });

            // then
            assertEquals(INVALID_CONCERT_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueQuery.Create 실패 - concertId가 0 이하인 경우")
        void fail_createQueueQueryTest2() {
            // given
            Long concertId = 0L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueQuery.Create(concertId);
            });

            // then
            assertEquals(INVALID_CONCERT_ID.getMessage(), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("QueueQuery.CreateTokenWaiting 테스트")
    class CreateTokenWaitingTests {
        @Test
        @DisplayName("QueueQuery.CreateTokenWaiting 성공")
        void createTokenWaitingTest() {
            // given
            String tokenId = "UUID";
            Long queueId = 1L;

            // when
            QueueQuery.CreateTokenWaiting query = new QueueQuery.CreateTokenWaiting(tokenId, queueId);

            // then
            assertNotNull(query);
        }

        @Test
        @DisplayName("QueueQuery.CreateTokenWaiting 실패 - tokenId가 null인 경우")
        void fail_createTokenWaitingTest1() {
            // given
            String tokenId = null;
            Long queueId = 1L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueQuery.CreateTokenWaiting(tokenId, queueId);
            });

            // then
            assertEquals(INVALID_TOKEN_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueQuery.CreateTokenWaiting 실패 - tokenId가 빈 문자열인 경우")
        void fail_createTokenWaitingTest2() {
            // given
            String tokenId = "";
            Long queueId = 1L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueQuery.CreateTokenWaiting(tokenId, queueId);
            });

            // then
            assertEquals(INVALID_TOKEN_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueQuery.CreateTokenWaiting 실패 - queueId가 null인 경우")
        void fail_createTokenWaitingTest3() {
            // given
            String tokenId = "UUID";
            Long queueId = null;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueQuery.CreateTokenWaiting(tokenId, queueId);
            });

            // then
            assertEquals(INVALID_QUEUE_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueQuery.CreateTokenWaiting 실패 - queueId가 0 이하인 경우")
        void fail_createTokenWaitingTest4() {
            // given
            String tokenId = "UUID";
            Long queueId = 0L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueQuery.CreateTokenWaiting(tokenId, queueId);
            });

            // then
            assertEquals(INVALID_QUEUE_ID.getMessage(), exception.getMessage());
        }
    }
}
