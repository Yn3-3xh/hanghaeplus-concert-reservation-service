package hanghaeplus.application.concurrency.point;

import hanghaeplus.application.IntegrationTest;
import hanghaeplus.application.point.dto.PointRequest;
import hanghaeplus.application.point.facade.PointFacade;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import hanghaeplus.domain.user.entity.User;
import hanghaeplus.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PointChargeConcurrencyTest extends IntegrationTest {

    @Autowired
    private PointFacade sut;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PointRepository pointRepository;

    String tokenId = "b9df2619-18cc-4515-9864-df2527d6a7de";

    @BeforeEach
    void setUp() {
        Long userId = 1L;

        User user = new User(userId, "User");
        userRepository.save(user);

        Point point = new Point(null, userId, 0, null);
        pointRepository.savePoint(point);

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);
    }

    @Test
    @DisplayName("포인트 충전 테스트 - 낙관적 락 - 한명의 유저에 대한 5번의 동시 요청에 대해 1번만 충전되도록 동시성 제어")
    void chargePointOptimisticLockTest() {
        // given
        Long userId = 1L;
        int testCount = 5;
        AtomicInteger optimisticExceptionCount = new AtomicInteger();
        int amount = 1000;
        PointRequest.PointCharge request = new PointRequest.PointCharge(tokenId, userId, amount);

        // when
        CompletableFuture<?>[] futures = IntStream.range(0, testCount)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    try {
                        sut.chargePoint(request);
                    } catch (ObjectOptimisticLockingFailureException e) {
                        optimisticExceptionCount.getAndIncrement();
                    }
                })).toArray(CompletableFuture[]::new);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        allOf.join();

        // then
        assertThat(optimisticExceptionCount.intValue()).isEqualTo(testCount - 1);

        Point savedPoint = pointRepository.findByUserId(userId).get();
        assertThat(savedPoint.getPoint()).isEqualTo(amount);
    }
}
