package hanghaeplus.domain.token.repository;

import hanghaeplus.domain.token.entity.Token;

import java.util.Optional;

public interface TokenRepository {

    Optional<Token> findByTokenId(String tokenId);

    void save(Token token);

    Optional<Token> findByUserId(Long userId);

    void deleteAll();
}
