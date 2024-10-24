package hanghaeplus.application.order.facade;

import hanghaeplus.application.order.dto.OrderRequest;
import hanghaeplus.application.point.error.PointErrorCode;
import hanghaeplus.domain.common.error.CoreException;
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
import hanghaeplus.domain.order.entity.Payment;
import hanghaeplus.domain.order.entity.enums.OrderStatus;
import hanghaeplus.domain.order.entity.enums.PaymentStatus;
import hanghaeplus.domain.order.entity.enums.PaymentType;
import hanghaeplus.domain.order.repository.OrderRepository;
import hanghaeplus.domain.order.repository.PaymentRepository;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("OrderFacade 통합 테스트")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderFacadeIntegrationTest {

    @Autowired
    private OrderFacade sut;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ConcertDetailRepository concertDetailRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @Autowired
    private QueueRepository queueRepository;

    private String tokenId = "b9df2619-18cc-4515-9864-df2527d6a7de";
    private Long userId = 1L;

    @BeforeEach
    void setUp() {
        Concert concert = new Concert(null, 1L, "2024 콘서트");
        concertRepository.insertConcert(concert);

        ConcertDetail concertDetail = new ConcertDetail(null, 1L, "Hall-A", 100,
                LocalDate.of(2024, 11, 15),
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5),
                ConcertDetailStatus.OPEN);
        concertDetailRepository.saveConcertDetail(concertDetail);

        List<Seat> seats = List.of(
                new Seat(null, 1L, "A-1", 20000, SeatStatus.EMPTY),
                new Seat(null, 1L, "B-1", 15000, SeatStatus.EMPTY)
        );
        seatRepository.saveSeats(seats);

        Queue queue = new Queue(null, 1L, 10);
        queueRepository.save(queue);

        QueueToken queueToken = QueueToken.createActivated(1L, 1L, tokenId);
        queueTokenRepository.save(queueToken);

        Reservation reservation = Reservation.createPending(1L, userId);
        reservationRepository.saveReservation(reservation);

        Order order = new Order(null, userId, 1L, 20000, OrderStatus.PROCESSING);
        orderRepository.saveOrder(order);
    }

    @AfterEach
    void tearDown() {
        pointRepository.deleteAll();
    }

    @Test
    @DisplayName("주문 결제 테스트 - 성공")
    void pass_orderPaymentTest() {
        // given
        Long orderId = 1L;
        int pointAmount = 30000;
        int useAmount = 20000;

        Point point = new Point(null, userId, pointAmount, null);
        pointRepository.savePoint(point);

        OrderRequest.paymentExecution request = new OrderRequest.paymentExecution(tokenId, orderId);

        // when
        sut.executePayment(request);

        // then
        Optional<Payment> resultPayment = paymentRepository.findById(1L);
        assertThat(resultPayment.get()).isEqualTo(new Payment(1L, 1L, 1L, useAmount, PaymentStatus.PAYED, PaymentType.POINT));

        Optional<Point> resultPoint = pointRepository.findByUserId(userId);
        assertThat(resultPoint.get().getPoint()).isEqualTo(pointAmount - useAmount);
    }

    @Test
    @DisplayName("주문 결제 테스트 - 실패 - 포인트 부족")
    void fail_orderPaymentTest1() {
        // given
        String tokenId = "b9df2619-18cc-4515-9864-df2527d6a7de";
        Long orderId = 1L;

        OrderRequest.paymentExecution request = new OrderRequest.paymentExecution(tokenId, orderId);

        // when
        CoreException exception = assertThrows(CoreException.class, () -> sut.executePayment(request));

        // then
        assertThat(exception.getMessage()).isEqualTo(PointErrorCode.INSUFFICIENT_POINTS.getMessage());
    }

    @Test
    @DisplayName("주문 결제 동시성 테스트 - 포인트 차감")
    void orderPaymentConcurrentTest() {
        // given
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        Long orderId = 1L;
        int pointAmount = 30000;
        int savedAmount = 10000;

        Point point = new Point(null, userId, pointAmount, null);
        pointRepository.savePoint(point);

        OrderRequest.paymentExecution request = new OrderRequest.paymentExecution(tokenId, orderId);

        // when
        for (int i = 0; i < 10; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                sut.executePayment(request);
            });
            futures.add(future);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        Throwable throwable = assertThrows(CompletionException.class, allOf::join);

        // then
        assertThat(throwable.getCause()).isInstanceOf(ObjectOptimisticLockingFailureException.class);

        Optional<Point> resultPoint = pointRepository.findByUserId(userId);
        assertThat(resultPoint.get().getPoint()).isEqualTo(savedAmount);
    }
}