package hanghaeplus.infra.token.repository;

import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import hanghaeplus.infra.token.jpa.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public void save(Token token) {
        tokenJpaRepository.save(token);
    }
}
