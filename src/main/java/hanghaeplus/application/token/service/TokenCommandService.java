package hanghaeplus.application.token.service;

import hanghaeplus.domain.token.dto.TokenCommand;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenCommandService {

    private final TokenRepository tokenRepository;

    public Token generateToken(TokenCommand.Create command) {
        UUID tokenId = UUID.randomUUID();
        LocalDateTime expiredAt = LocalDateTime.now().plusHours(1);

        Token token = Token.create(tokenId, command.userId(), expiredAt);
        tokenRepository.save(token);

        return token;
    }
}
