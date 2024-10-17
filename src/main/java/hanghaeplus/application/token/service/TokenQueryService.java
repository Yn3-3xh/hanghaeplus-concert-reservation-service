package hanghaeplus.application.token.service;

import hanghaeplus.domain.token.dto.TokenQuery;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static hanghaeplus.application.token.error.TokenErrorCode.NO_SUCH_TOKEN;

@Service
@RequiredArgsConstructor
public class TokenQueryService {

    private final TokenRepository tokenRepository;

    @Transactional(readOnly = true)
    public Long getUserId(TokenQuery.Create query) {
        Token token = tokenRepository.findByTokenId(query.tokenId())
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_TOKEN.getMessage()));

        return token.getUserId();
    }
}
