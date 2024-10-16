package hanghaeplus.application.token.facade;

import hanghaeplus.application.token.dto.TokenRequest;
import hanghaeplus.application.token.service.TokenCommandService;
import hanghaeplus.domain.token.dto.TokenCommand;
import hanghaeplus.application.token.dto.TokenResponse;
import hanghaeplus.domain.token.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenFacade {

    private final TokenCommandService tokenService;

    public TokenResponse.EnrollToken generateToken(TokenRequest.EnrollToken enrollToken) {
        Token token = tokenService.generateToken(
                new TokenCommand.Create(enrollToken.userId()));

        return new TokenResponse.EnrollToken(token.getId());
    }
}