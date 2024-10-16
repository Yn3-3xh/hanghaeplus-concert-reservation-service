package hanghaeplus.domain.token.dto;

import hanghaeplus.domain.token.error.TokenErrorCode;

public class TokenCommand {

    public record Create(
            Long userId
    ) {
        public Create {
            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException(TokenErrorCode.INVALID_USER_ID.getMessage());
            }
        }
    }
}
