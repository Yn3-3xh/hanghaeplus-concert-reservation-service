package hanghaeplus.application.token.facade;

import hanghaeplus.application.token.dto.TokenRequest;
import hanghaeplus.application.token.dto.TokenResponse;
import hanghaeplus.application.token.service.TokenCommandService;
import hanghaeplus.domain.token.dto.TokenCommand;
import hanghaeplus.domain.token.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenFacade {

    private final TokenCommandService tokenCommandService;

    public TokenResponse.EnrollToken generateToken(TokenRequest.EnrollToken request) {
        Token token = tokenCommandService.generateToken(new TokenCommand.Create(request.userId()));
        return new TokenResponse.EnrollToken(token.getTokenId());
    }
}