package hanghaeplus.application.order.facade;

import hanghaeplus.api.event.OrderCompletedEventListener;
import hanghaeplus.api.event.PaymentEventListener;
import hanghaeplus.application.IntegrationTest;
import hanghaeplus.application.order.dto.OrderRequest;
import hanghaeplus.domain.concert.entity.Concert;
import hanghaeplus.domain.concert.entity.ConcertDetail;
import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.concert.entity.enums.ConcertDetailStatus;
import hanghaeplus.domain.concert.entity.enums.ReservationStatus;
import hanghaeplus.domain.concert.entity.enums.SeatStatus;
import hanghaeplus.domain.concert.repository.ConcertDetailRepository;
import hanghaeplus.domain.concert.repository.ConcertRepository;
import hanghaeplus.domain.concert.repository.ReservationRepository;
import hanghaeplus.domain.concert.repository.SeatRepository;
import hanghaeplus.domain.event.OrderCompletedEvent;
import hanghaeplus.domain.event.PaymentSuccessEvent;
import hanghaeplus.domain.order.entity.Order;
import hanghaeplus.domain.order.entity.enums.OrderStatus;
import hanghaeplus.domain.order.repository.OrderRepository;
import hanghaeplus.domain.order.repository.PaymentRepository;
import hanghaeplus.domain.point.entity.Point;
import hanghaeplus.domain.point.repository.PointRepository;
import hanghaeplus.domain.queue.entity.Queue;
import hanghaeplus.domain.queue.entity.QueueToken;
import hanghaeplus.domain.queue.repository.QueueRepository;
import hanghaeplus.domain.queue.repository.QueueTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("결제 이벤트 동작 테스트")
public class OrderPaymentEventTest extends IntegrationTest {

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

    @SpyBean
    private OrderCompletedEventListener orderCompletedEventListener;

    @SpyBean
    private PaymentEventListener paymentEventListener;

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

    @Test
    @DisplayName("[성공] 주문 결제 완료 - 상태 변경 이벤트 동작 테스트")
    void pass_orderPaymentSuccessEventTest1() {
        // given
        Long orderId = 1L;
        int pointAmount = 30000;

        Point point = new Point(null, userId, pointAmount, null);
        pointRepository.savePoint(point);

        OrderRequest.paymentExecution request = new OrderRequest.paymentExecution(tokenId, orderId);

        // when
        sut.executePayment(request);

        // then
        verify(orderCompletedEventListener, times(1)).handleOrderCompleted(any(OrderCompletedEvent.Completed.class));
    }

    @Test
    @DisplayName("[성공] 주문 결제 완료 - 상태 변경 이벤트 테스트")
    void pass_orderPaymentSuccessEventTest2() throws InterruptedException {
        // given
        Long orderId = 1L;
        int pointAmount = 30000;

        Point point = new Point(null, userId, pointAmount, null);
        pointRepository.savePoint(point);

        OrderRequest.paymentExecution request = new OrderRequest.paymentExecution(tokenId, orderId);

        // when
        sut.executePayment(request);
        Thread.sleep(5000);
        reservationRepository.flush();

        // then
        Reservation reservation = reservationRepository.findById(1L).get();
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);

        Seat seat = seatRepository.findById(1L).get();
        assertThat(seat.getStatus()).isEqualTo(SeatStatus.RESERVATION);
    }

    @Test
    @DisplayName("[성공] 주문 결제 완료 - 알림 이벤트 동작 테스트")
    void pass_orderPaymentSuccessEventTest3() {
        // given
        Long orderId = 1L;
        int pointAmount = 30000;

        Point point = new Point(null, userId, pointAmount, null);
        pointRepository.savePoint(point);

        OrderRequest.paymentExecution request = new OrderRequest.paymentExecution(tokenId, orderId);

        // when
        sut.executePayment(request);

        // then
        verify(paymentEventListener, times(1)).paymentSuccessHandler(any(PaymentSuccessEvent.Success.class));
    }
}
