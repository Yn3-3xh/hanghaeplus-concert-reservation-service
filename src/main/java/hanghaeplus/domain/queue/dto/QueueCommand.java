package hanghaeplus.domain.queue.dto;

import static hanghaeplus.domain.queue.error.QueueErrorCode.*;

public class QueueCommand {

    public record CreateTokenWaiting (
            String tokenId,
            Long queueId
    ) {
        public CreateTokenWaiting {
            if (tokenId == null || tokenId.isEmpty()) {
                throw new IllegalArgumentException(INVALID_TOKEN_ID.getMessage());
            }
            if (queueId == null || queueId <= 0) {
                throw new IllegalArgumentException(INVALID_QUEUE_ID.getMessage());
            }
        }
    }

    public record CreateTokenActivated (
            Long queueTokenId,
            String tokenId,
            Long queueId
    ) {
        public CreateTokenActivated {
            if (queueTokenId == null || queueTokenId <= 0) {
                throw new IllegalArgumentException(INVALID_QUEUE_TOKEN_ID.getMessage());
            }
            if (tokenId == null || tokenId.isEmpty()) {
                throw new IllegalArgumentException(INVALID_TOKEN_ID.getMessage());
            }
            if (queueId == null || queueId <= 0) {
                throw new IllegalArgumentException(INVALID_QUEUE_ID.getMessage());
            }
        }
    }
}
