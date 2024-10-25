package hanghaeplus.application.token.service;

import hanghaeplus.application.token.error.TokenErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.token.dto.TokenQuery;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenQueryService {

    private final TokenRepository tokenRepository;

    @Transactional(readOnly = true)
    public Token getToken(TokenQuery.Create query) {
        return tokenRepository.findByTokenId(query.tokenId())
                .orElseThrow(() -> new CoreException(TokenErrorCode.NOT_FOUND_TOKEN));
    }
}
