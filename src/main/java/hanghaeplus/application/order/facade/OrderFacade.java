package hanghaeplus.application.order.facade;

import hanghaeplus.application.concert.service.*;
import hanghaeplus.application.event.PaymentEventPublisher;
import hanghaeplus.application.order.dto.OrderRequest;
import hanghaeplus.application.order.service.OrderCommandService;
import hanghaeplus.application.order.service.OrderQueryService;
import hanghaeplus.application.order.service.PaymentCommandService;
import hanghaeplus.application.queue.service.QueueQueryService;
import hanghaeplus.application.queue.service.QueueTokenCommandService;
import hanghaeplus.domain.concert.dto.ConcertQuery;
import hanghaeplus.domain.concert.dto.ReservationQuery;
import hanghaeplus.domain.concert.dto.SeatQuery;
import hanghaeplus.domain.concert.entity.ConcertDetail;
import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.concert.entity.Seat;
import hanghaeplus.domain.event.PaymentSuccessEvent;
import hanghaeplus.domain.order.dto.OrderCommand;
import hanghaeplus.domain.order.dto.OrderQuery;
import hanghaeplus.domain.order.dto.PaymentCommand;
import hanghaeplus.domain.order.entity.Order;
import hanghaeplus.domain.queue.dto.QueueQuery;
import hanghaeplus.domain.queue.dto.QueueTokenCommand;
import hanghaeplus.domain.queue.entity.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderQueryService orderQueryService;
    private final ReservationQueryService reservationQueryService;
    private final SeatQueryService seatQueryService;
    private final ConcertDetailQueryService concertDetailQueryService;
    private final QueueQueryService queueQueryService;

    private final PaymentCommandService paymentCommandService;
    private final OrderCommandService orderCommandService;
    private final ReservationCommandService reservationCommandService;
    private final SeatCommandService seatCommandService;
    private final QueueTokenCommandService queueTokenCommandService;

    private final PaymentEventPublisher paymentEventPublisher;

    @Transactional
    public void executePayment(OrderRequest.paymentExecution request) {
        // 주문 결제
        Order order = orderQueryService.getAvailableOrder(new OrderQuery.CreateAvailableOrder(request.orderId()));
        paymentCommandService.executePayment(new PaymentCommand.Create(order.getUserId(), order.getId(), order.getAmount()));

        // 상태 변경
        order.domainOperation();
        orderCommandService.updateOrderCompleted(new OrderCommand.CreateOrderCompleted(order.getId()));

        // 대기열 제거
        Reservation reservation = reservationQueryService.getReservation(new ReservationQuery.CreateReservation(order.getReservationId()));
        Seat seat = seatQueryService.getSeat(new SeatQuery.CreateSeat(reservation.getSeatId()));
        ConcertDetail concertDetail = concertDetailQueryService.getConcertDetail(new ConcertQuery.CreateConcertDetail(seat.getConcertDetailId()));
        Queue queue = queueQueryService.getQueue(new QueueQuery.Create(concertDetail.getConcertId()));
        queueTokenCommandService.deleteQueueToken(new QueueTokenCommand.CreateQueueTokenDelete(queue.getId(), request.tokenId()));

        // 결제 알림 이벤트
        paymentEventPublisher.success(new PaymentSuccessEvent.Success(order.getId(), order.getUserId(), seat.getId()));
    }
}
