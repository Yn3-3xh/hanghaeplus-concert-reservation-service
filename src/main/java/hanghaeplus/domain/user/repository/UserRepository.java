package hanghaeplus.domain.user.repository;

import hanghaeplus.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long userId);
}
