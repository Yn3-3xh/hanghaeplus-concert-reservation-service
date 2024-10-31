package hanghaeplus.application.concurrency.concert;

import hanghaeplus.application.IntegrationTest;
import hanghaeplus.application.concert.dto.ConcertRequest;
import hanghaeplus.application.concert.facade.ConcertFacade;
import hanghaeplus.domain.concert.entity.Concert;
import hanghaeplus.domain.concert.entity.ConcertDetail;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.entity.enums.ConcertDetailStatus;
import hanghaeplus.domain.concert.entity.enums.SeatStatus;
import hanghaeplus.domain.concert.repository.ConcertDetailRepository;
import hanghaeplus.domain.concert.repository.ConcertRepository;
import hanghaeplus.domain.concert.repository.SeatRepository;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import hanghaeplus.domain.token.entity.Token;
import hanghaeplus.domain.token.repository.TokenRepository;
import hanghaeplus.domain.user.entity.User;
import hanghaeplus.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SeatReservationConcurrencyTest extends IntegrationTest {

    @Autowired
    private ConcertFacade sut;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertDetailRepository concertDetailRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @Autowired
    private SeatRepository seatRepository;

    String tokenId = "b9df2619-18cc-4515-9864-df2527d6a7de";

    @BeforeEach
    void setUp() {
        Long userId = 1L;
        User user = new User(userId, "User");
        userRepository.save(user);

        int pointAmount = 15000;
        Point point = new Point(null, userId, pointAmount, null);
        pointRepository.savePoint(point);

        Token token = Token.create(tokenId, userId);
        tokenRepository.save(token);

        Long concertId = 1L;
        Concert concert = new Concert(concertId, 1L, "콘서트A");
        concertRepository.insertConcert(concert);

        Long concertDetailId = 1L;
        ConcertDetail concertDetail = new ConcertDetail(concertDetailId, 1L, "Hall-A", 100,
                LocalDate.of(2024, 11, 15),
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5),
                ConcertDetailStatus.OPEN);
        concertDetailRepository.saveConcertDetail(concertDetail);

        Long queueId = 1L;
        Queue queue = new Queue(queueId, concertId, 50);
        queueRepository.save(queue);

        Long queueTokenId = 1L;
        QueueToken queueToken = QueueToken.createActivated(queueTokenId, queueId, tokenId);
        queueTokenRepository.save(queueToken);

        int seatAmount = 10000;
        Seat seat = new Seat(null, concertDetailId, "A-1", seatAmount, SeatStatus.EMPTY);
        seatRepository.save(seat);
    }

    @Test
    @DisplayName("좌석 임시 예약 테스트 - 낙관적 락 - 30명의 유저가 한 좌석의 동시 요청에 대해 동시성 제어")
    void seatReservationOptimisticLockTest() {
        // given
        Long concertId = 1L;
        Long detailId = 1L;
        Long seatId = 1L;
        int testCount = 30;
        AtomicInteger optimisticExceptionCount = new AtomicInteger();

        ConcertRequest.SeatReservation request = new ConcertRequest.SeatReservation(tokenId, concertId, detailId, seatId);

        // when
        CompletableFuture<?>[] futures = IntStream.range(0, testCount)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    sut.reserveConcertSeat(request);
                }).exceptionally(ex -> {
                    optimisticExceptionCount.getAndIncrement();
                    return null;
                })).toArray(CompletableFuture[]::new);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        allOf.join();

        // then
        assertThat(optimisticExceptionCount.intValue()).isEqualTo(testCount - 1);
    }

    @Test
    @DisplayName("좌석 임시 예약 테스트 - 비관적 락 - 30명의 유저가 한 좌석의 동시 요청에 대해 동시성 제어")
    void seatReservationPessimisticLockTest() {
        // given
        Long concertId = 1L;
        Long detailId = 1L;
        Long seatId = 1L;
        int testCount = 30;
        AtomicInteger pessimisticExceptionCount = new AtomicInteger();

        ConcertRequest.SeatReservation request = new ConcertRequest.SeatReservation(tokenId, concertId, detailId, seatId);

        // when
        CompletableFuture<?>[] futures = IntStream.range(0, testCount)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    sut.reserveConcertSeat(request);
                }).exceptionally(ex -> {
                    pessimisticExceptionCount.getAndIncrement();
                    return null;
                })).toArray(CompletableFuture[]::new);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        allOf.join();

        // then
        assertThat(pessimisticExceptionCount.intValue()).isEqualTo(testCount - 1);
    }

    @Test
    @DisplayName("좌석 임시 예약 테스트 - 분산락 - 30명의 유저가 한 좌석의 동시 요청에 대해 동시성 제어")
    void seatReservationDistributedLockTest() {
        // given
        Long concertId = 1L;
        Long detailId = 1L;
        Long seatId = 1L;
        int testCount = 30;
        AtomicInteger pessimisticExceptionCount = new AtomicInteger();

        ConcertRequest.SeatReservation request = new ConcertRequest.SeatReservation(tokenId, concertId, detailId, seatId);

        // when
        CompletableFuture<?>[] futures = IntStream.range(0, testCount)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    sut.reserveConcertSeat(request);
                }).exceptionally(ex -> {
                    pessimisticExceptionCount.getAndIncrement();
                    return null;
                })).toArray(CompletableFuture[]::new);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        allOf.join();

        // then
        assertThat(pessimisticExceptionCount.intValue()).isEqualTo(testCount - 1);
    }
}
