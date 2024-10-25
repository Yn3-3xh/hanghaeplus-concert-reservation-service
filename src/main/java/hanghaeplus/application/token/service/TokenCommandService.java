package hanghaeplus.application.token.service;

import hanghaeplus.domain.token.dto.TokenCommand;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenCommandService {

    private final TokenRepository tokenRepository;

    @Transactional
    public Token generateToken(TokenCommand.Create command) {
        UUID tokenId = UUID.randomUUID();
        Token token = Token.create(tokenId.toString(), command.userId());
        tokenRepository.save(token);

        return token;
    }
}
