package hanghaeplus.infra.token.jpa;

import hanghaeplus.domain.token.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenJpaRepository extends CrudRepository<Token, Long> {
}
