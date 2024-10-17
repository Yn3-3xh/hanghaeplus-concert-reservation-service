package hanghaeplus.domain.point.dto;

import hanghaeplus.domain.point.entity.enums.PointStatus;

import static hanghaeplus.domain.point.error.PointErrorCode.*;

public class PointCommand {

    public record Create (
            Long userId,
            int amount
    ) {
        public Create {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException(INVALID_USER_ID.getMessage());
            }
            if (amount <= 0) {
                throw new IllegalArgumentException(INVALID_AMOUNT.getMessage());
            }
        }
    }

    public record CreatePointHistory (
            Long userId,
            int amount,
            PointStatus status
    ) {
        public CreatePointHistory {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException(INVALID_USER_ID.getMessage());
            }
            if (amount <= 0) {
                throw new IllegalArgumentException(INVALID_AMOUNT.getMessage());
            }
            if (status == null) {
                throw new IllegalArgumentException(INVALID_STATUS.getMessage());
            }
        }
    }
}
