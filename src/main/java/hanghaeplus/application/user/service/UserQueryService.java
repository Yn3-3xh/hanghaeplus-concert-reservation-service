package hanghaeplus.application.user.service;

import hanghaeplus.application.user.error.UserErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.user.dto.UserQuery;
import hanghaeplus.domain.user.entity.User;
import hanghaeplus.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User selectUser(UserQuery.Create query) {
        return userRepository.findById(query.userId())
                .orElseThrow(() -> new CoreException(UserErrorCode.NOT_FOUND_USER));
    }
}
