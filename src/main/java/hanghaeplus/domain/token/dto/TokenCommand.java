package hanghaeplus.domain.token.dto;

import static hanghaeplus.domain.token.error.TokenErrorCode.INVALID_USER_ID;

public class TokenCommand {

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
