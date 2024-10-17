package hanghaeplus.domain.queue.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.queue.error.QueueErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QueueCommand 단위 테스트")
class QueueCommandUnitTest {

    @Nested
    @DisplayName("QueueCommand.CreateTokenWaiting 테스트")
    class CreateTokenWaitingTests {

        @Test
        @DisplayName("QueueCommand.CreateTokenWaiting 성공")
        void pass_createTokenWaitingTest() {
            // given
            String tokenId = "UUID";
            Long queueId = 1L;

            // when
            QueueCommand.CreateTokenWaiting command = new QueueCommand.CreateTokenWaiting(tokenId, queueId);

            // then
            assertNotNull(command);
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenWaiting 실패 - tokenId가 null인 경우")
        void fail_createTokenWaitingTest1() {
            // given
            String tokenId = null;
            Long queueId = 1L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenWaiting(tokenId, queueId);
            });

            // then
            assertEquals(INVALID_TOKEN_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenWaiting 실패 - tokenId가 빈 문자열인 경우")
        void fail_createTokenWaitingTest2() {
            // given
            String tokenId = "";
            Long queueId = 1L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenWaiting(tokenId, queueId);
            });

            // then
            assertEquals(INVALID_TOKEN_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenWaiting 실패 - queueId가 null인 경우")
        void fail_createTokenWaitingTest3() {
            // given
            String tokenId = "UUID";
            Long queueId = null;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenWaiting(tokenId, queueId);
            });

            // then
            assertEquals(INVALID_QUEUE_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenWaiting 실패 - queueId가 0 이하인 경우")
        void fail_createTokenWaitingTest4() {
            // given
            String tokenId = "UUID";
            Long queueId = 0L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenWaiting(tokenId, queueId);
            });

            // then
            assertEquals(INVALID_QUEUE_ID.getMessage(), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("QueueCommand.CreateTokenActivated 테스트")
    class CreateTokenActivatedTests {

        @Test
        @DisplayName("QueueCommand.CreateTokenActivated 성공")
        void pass_createTokenActivatedTest() {
            // given
            Long queueTokenId = 1L;
            String tokenId = "UUID";
            Long queueId = 1L;

            // when
            QueueCommand.CreateTokenActivated command = new QueueCommand.CreateTokenActivated(queueTokenId, tokenId, queueId);

            // then
            assertNotNull(command);
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenActivated 실패 - queueTokenId가 null인 경우")
        void fail_createTokenActivatedTest1() {
            // given
            Long queueTokenId = null;
            String tokenId = "UUID";
            Long queueId = 1L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenActivated(queueTokenId, tokenId, queueId);
            });

            // then
            assertEquals(INVALID_QUEUE_TOKEN_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenActivated 실패 - queueTokenId가 0 이하인 경우")
        void fail_createTokenActivatedTest2() {
            // given
            Long queueTokenId = 0L;
            String tokenId = "UUID";
            Long queueId = 1L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenActivated(queueTokenId, tokenId, queueId);
            });

            // then
            assertEquals(INVALID_QUEUE_TOKEN_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenActivated 실패 - tokenId가 null인 경우")
        void fail_createTokenActivatedTest3() {
            // given
            Long queueTokenId = 1L;
            String tokenId = null;
            Long queueId = 1L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenActivated(queueTokenId, tokenId, queueId);
            });

            // then
            assertEquals(INVALID_TOKEN_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenActivated 실패 - tokenId가 빈 문자열인 경우")
        void fail_createTokenActivatedTest4() {
            // given
            Long queueTokenId = 1L;
            String tokenId = "";
            Long queueId = 1L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenActivated(queueTokenId, tokenId, queueId);
            });

            // then
            assertEquals(INVALID_TOKEN_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenActivated 실패 - queueId가 null인 경우")
        void fail_createTokenActivatedTest5() {
            // given
            Long queueTokenId = 1L;
            String tokenId = "UUID";
            Long queueId = null;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenActivated(queueTokenId, tokenId, queueId);
            });

            // then
            assertEquals(INVALID_QUEUE_ID.getMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("QueueCommand.CreateTokenActivated 실패 - queueId가 0 이하인 경우")
        void fail_createTokenActivatedTest6() {
            // given
            Long queueTokenId = 1L;
            String tokenId = "UUID";
            Long queueId = 0L;

            // when
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new QueueCommand.CreateTokenActivated(queueTokenId, tokenId, queueId);
            });

            // then
            assertEquals(INVALID_QUEUE_ID.getMessage(), exception.getMessage());
        }
    }
}