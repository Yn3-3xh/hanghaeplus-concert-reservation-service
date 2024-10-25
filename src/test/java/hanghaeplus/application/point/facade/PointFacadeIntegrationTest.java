package hanghaeplus.application.point.facade;

import hanghaeplus.application.point.dto.PointRequest;
import hanghaeplus.application.point.dto.PointResponse;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.entity.PointHistory;
import hanghaeplus.domain.point.entity.enums.PointStatus;
import hanghaeplus.domain.point.repository.PointHistoryRepository;
import hanghaeplus.domain.point.repository.PointRepository;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("PointFacade 통합 테스트")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PointFacadeIntegrationTest {

    @Autowired
    private PointFacade sut;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    private String tokenId = "b9df2619-18cc-4515-9864-df2527d6a7de";
    private Long userId = 2L;

    @BeforeEach
    void setUp() {
        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);
    }

    @AfterEach
    void tearDown() {
        pointRepository.deleteAll();
        pointHistoryRepository.deleteAll();
    }

    @Test
    @DisplayName("포인트 조회 테스트 - 통과")
    void pass_selectPointTest() {
        // given
        int amount = 30000;
        Point point = new Point(null, userId, amount, null);
        pointRepository.savePoint(point);

        PointRequest.PointSelection request = new PointRequest.PointSelection(tokenId, userId);

        // when
        PointResponse.PointSelection result = sut.selectPoint(request);

        // then
        assertThat(result.point()).isEqualTo(amount);
    }

    @Test
    @DisplayName("포인트 조회 테스트 - 실패 - 토큰이 없는 경우")
    void fail_selectPointTest() {
        // given
        int amount = 30000;
        Point point = new Point(null, userId, amount, null);
        pointRepository.savePoint(point);

        PointRequest.PointSelection request = new PointRequest.PointSelection(tokenId, userId);

        // when
        PointResponse.PointSelection result = sut.selectPoint(request);

        // then
        assertThat(result.point()).isEqualTo(amount);
    }

    @Test
    @DisplayName("포인트 충전 테스트 - 통과")
    void pass_chargePoint() {
        // given
        int amount = 30000;
        int chargeAmount = 1000;
        Point point = new Point(null, userId, amount, null);
        pointRepository.savePoint(point);

        PointRequest.PointCharge request = new PointRequest.PointCharge(tokenId, userId, chargeAmount);

        // when
        sut.chargePoint(request);

        // then
        Optional<Point> resultPoint = pointRepository.findByUserId(userId);
        assertThat(resultPoint.get().getPoint()).isEqualTo(amount + chargeAmount);

        List<PointHistory> resultPointHistory = pointHistoryRepository.findByUserId(userId);
        assertThat(resultPointHistory.get(0)).isEqualTo(new PointHistory(1L, userId, chargeAmount, PointStatus.CHARGE));
    }

    @Test
    @DisplayName("포인트 충전 동시성 테스트")
    void chargePointConcurrentTest() {
        // given
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        int amount = 30000;
        int chargeAmount = 2000;
        Point point = new Point(null, userId, amount, null);
        pointRepository.savePoint(point);

        PointRequest.PointCharge request = new PointRequest.PointCharge(tokenId, userId, chargeAmount);

        // when
        for (int i = 0; i < 5; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                sut.chargePoint(request);
            });
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        Throwable throwable = assertThrows(CompletionException.class, allOf::join);

        // then
        assertThat(throwable.getCause()).isInstanceOf(ObjectOptimisticLockingFailureException.class);

        Optional<Point> resultPoint = pointRepository.findByUserId(userId);
        assertThat(resultPoint.get().getPoint()).isEqualTo(amount + chargeAmount);

        List<PointHistory> resultPointHistory = pointHistoryRepository.findByUserId(userId);
        assertThat(resultPointHistory.size()).isEqualTo(1);
        assertThat(resultPointHistory.get(0))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new PointHistory(null, userId, chargeAmount, PointStatus.CHARGE));
    }
}