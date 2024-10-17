package hanghaeplus.domain.point.dto;

import static hanghaeplus.domain.point.error.PointErrorCode.INVALID_USER_ID;

public class PointQuery {

    public record Create(
            Long userId
    ) {
        public Create {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException(INVALID_USER_ID.getMessage());
            }
        }
    }
}
