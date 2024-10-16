package hanghaeplus.application.user.service;

import hanghaeplus.domain.user.dto.UserCommand;
import hanghaeplus.domain.user.entity.User;
import hanghaeplus.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User selectUser(UserCommand.CreateUser command) {
        return userRepository.findById(command.userId())
                .orElseThrow(() -> new NoSuchElementException("등록된 사용자가 아닙니다."));
    }
}
