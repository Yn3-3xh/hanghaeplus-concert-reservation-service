package hanghaeplus.domain.queue.dto;

import static hanghaeplus.domain.queue.error.QueueErrorCode.*;

public class QueueQuery {

    public record Create (
            Long concertId
    ) {
        public Create {
            if (concertId == null || concertId <= 0) {
                throw new IllegalArgumentException(INVALID_CONCERT_ID.getMessage());
            }
        }
    }

    public record CreateTokenWaiting (
            String tokenId,
            Long queueId
    ) {
        public CreateTokenWaiting {
            if (tokenId == null || tokenId.trim().isEmpty()) {
                throw new IllegalArgumentException(INVALID_TOKEN_ID.getMessage());
            }
            if (queueId == null || queueId <= 0) {
                throw new IllegalArgumentException(INVALID_QUEUE_ID.getMessage());
            }
        }
    }

    public record CreateToken (
            String tokenId
    ) {
        public CreateToken {
            if (tokenId == null || tokenId.trim().isEmpty()) {
                throw new IllegalArgumentException(INVALID_TOKEN_ID.getMessage());
            }
        }
    }
}
