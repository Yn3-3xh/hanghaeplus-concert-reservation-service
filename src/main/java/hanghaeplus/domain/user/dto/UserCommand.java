package hanghaeplus.domain.user.dto;

import static hanghaeplus.domain.user.error.UserErrorCode.INVALID_USER_ID;

public class UserCommand {

    public record CreateUser (
            Long userId
    ) {
        public CreateUser {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException(INVALID_USER_ID.getMessage());
            }
        }
    }
}
