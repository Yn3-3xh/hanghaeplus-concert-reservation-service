package hanghaeplus.infra.user.jpa;

import hanghaeplus.domain.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepository extends CrudRepository<User, Long> {
}
