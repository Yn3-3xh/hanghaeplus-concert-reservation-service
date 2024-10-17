package hanghaeplus.infra.token.jpa;

import hanghaeplus.domain.token.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenJpaRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByTokenId(String tokenId);
}
