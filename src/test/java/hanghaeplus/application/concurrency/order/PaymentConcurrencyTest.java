package hanghaeplus.application.concurrency.order;

import hanghaeplus.application.IntegrationTest;
import hanghaeplus.application.order.dto.OrderRequest;
import hanghaeplus.application.order.facade.OrderFacade;
import hanghaeplus.domain.concert.entity.Concert;
import hanghaeplus.domain.concert.entity.ConcertDetail;
import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.entity.enums.ConcertDetailStatus;
import hanghaeplus.domain.concert.entity.enums.SeatStatus;
import hanghaeplus.domain.concert.repository.ConcertDetailRepository;
import hanghaeplus.domain.concert.repository.ConcertRepository;
import hanghaeplus.domain.concert.repository.ReservationRepository;
import hanghaeplus.domain.concert.repository.SeatRepository;
import hanghaeplus.domain.order.entity.Order;
import hanghaeplus.domain.order.entity.enums.OrderStatus;
import hanghaeplus.domain.order.repository.OrderRepository;
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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PaymentConcurrencyTest extends IntegrationTest {

    @Autowired
    private OrderFacade sut;

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

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private OrderRepository orderRepository;

    String tokenId = "b9df2619-18cc-4515-9864-df2527d6a7de";
    int pointAmount = 15000;
    int seatAmount = 10000;

    @BeforeEach
    void setUp() {
        Long userId = 1L;
        User user = new User(userId, "User");
        userRepository.save(user);

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
        QueueToken queueToken = QueueToken.create(queueTokenId, tokenId);
        queueTokenRepository.insertActivatedQueueTokens(List.of(queueToken));

        Long seatId = 1L;
        Seat seat = new Seat(null, concertDetailId, "A-1", seatAmount, SeatStatus.PENDING);
        seatRepository.save(seat);

        Reservation reservation = Reservation.createPending(seatId, userId);
        reservationRepository.saveReservation(reservation);

        Long orderId = 1L;
        Order order = new Order(orderId, userId, 1L, seatAmount, OrderStatus.PROCESSING);
        orderRepository.saveOrder(order);
    }

    @Test
    @DisplayName("결제(포인트 사용) 테스트 - 낙관적 락 - 5번의 동시 요청에 대해 1번만 충전되도록 동시성 제어")
    void OrderPaymentOptimisticLockTest() {
        // given
        Long userId = 1L;
        Long orderId = 1L;
        int testCount = 10;
        AtomicInteger optimisticExceptionCount = new AtomicInteger();
        OrderRequest.paymentExecution request = new OrderRequest.paymentExecution(tokenId, orderId);

        // when
        CompletableFuture<?>[] futures = IntStream.range(0, testCount)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    sut.executePayment(request);
                }).exceptionally(ex -> {
                    optimisticExceptionCount.getAndIncrement();
                    return null;
                }))
                .toArray(CompletableFuture[]::new);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        allOf.join();

        // then
        assertThat(optimisticExceptionCount.intValue()).isEqualTo(testCount - 1);

        Point savedPoint = pointRepository.findByUserId(userId).get();
        assertThat(savedPoint.getPoint()).isEqualTo(pointAmount - seatAmount);
    }

    @Test
    @DisplayName("결제(포인트 사용) 테스트 - 비관적 락 - 5번의 동시 요청에 대해 1번만 충전되도록 동시성 제어")
    void OrderPaymentPessimisticLockTest() {
        // given
        Long userId = 1L;
        Long orderId = 1L;
        int testCount = 10;
        AtomicInteger pessimisticExceptionCount = new AtomicInteger();
        OrderRequest.paymentExecution request = new OrderRequest.paymentExecution(tokenId, orderId);

        // when
        CompletableFuture<?>[] futures = IntStream.range(0, testCount)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    sut.executePayment(request);
                }).exceptionally(ex -> {
                    pessimisticExceptionCount.getAndIncrement();
                    return null;
                }))
                .toArray(CompletableFuture[]::new);
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        allOf.join();

        // then
        assertThat(pessimisticExceptionCount.intValue()).isEqualTo(testCount - 1);

        Point savedPoint = pointRepository.findByUserId(userId).get();
        assertThat(savedPoint.getPoint()).isEqualTo(pointAmount - seatAmount);
    }
}
