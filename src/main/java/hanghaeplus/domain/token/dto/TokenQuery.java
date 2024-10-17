package hanghaeplus.domain.token.dto;

import static hanghaeplus.domain.token.error.TokenErrorCode.INVALID_TOKEN_ID;

public class TokenQuery {

    public record Create (
            String tokenId
    ) {
        public Create {
            if (tokenId == null || tokenId.trim().isEmpty()) {
                throw new IllegalArgumentException(INVALID_TOKEN_ID.getMessage());
            }
        }
    }
}
