package hanghaeplus.api.token.mapper;

import hanghaeplus.application.token.dto.TokenRequest;
import hanghaeplus.application.token.dto.TokenResponse;
import org.openapi.model.TokenHttpRequest;
import org.openapi.model.TokenHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public TokenRequest.EnrollToken toDto(TokenHttpRequest tokenHttpRequest) {
        return new TokenRequest.EnrollToken(tokenHttpRequest.getUserId());
    }

    public TokenHttpResponse fromDto(TokenResponse.EnrollToken enrollTokenResponse) {
        return new TokenHttpResponse(enrollTokenResponse.tokenId());
    }
}
