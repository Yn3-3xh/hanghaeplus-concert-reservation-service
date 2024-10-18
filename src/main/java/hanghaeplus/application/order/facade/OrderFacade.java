package hanghaeplus.application.order.facade;

import hanghaeplus.application.concert.service.ReservationCommandService;
import hanghaeplus.application.concert.service.ReservationQueryService;
import hanghaeplus.application.concert.service.SeatCommandService;
import hanghaeplus.application.order.dto.OrderRequest;
import hanghaeplus.application.order.service.OrderCommandService;
import hanghaeplus.application.order.service.OrderQueryService;
import hanghaeplus.application.order.service.PaymentCommandService;
import hanghaeplus.application.queue.service.QueueTokenCommandService;
import hanghaeplus.domain.concert.entity.Reservation;
import hanghaeplus.domain.order.dto.OrderQuery;
import hanghaeplus.domain.order.dto.PaymentCommand;
import hanghaeplus.domain.order.entity.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderQueryService orderQueryService;
    private final ReservationQueryService reservationQueryService;

    private final PaymentCommandService paymentCommandService;
    private final OrderCommandService orderCommandService;
    private final ReservationCommandService reservationCommandService;
    private final SeatCommandService seatCommandService;
    private final QueueTokenCommandService queueTokenCommandService;

    @Transactional
    public void executePayment(OrderRequest.paymentExecution request) {
        Order order = orderQueryService.getAvailableOrder(
                new OrderQuery.CreateAvailableOrder(request.orderId()));

        paymentCommandService.executePayment(
                new PaymentCommand.Create(order.getUserId(), order.getId(), order.getAmount()));

        orderCommandService.updateOrderCompleted(order.getId());
        Reservation reservation = reservationQueryService.getReservation(order.getReservationId());
        reservationCommandService.updateReservationCompleted(reservation.getId());
        seatCommandService.updateSeatCompleted(reservation.getSeatId());
        queueTokenCommandService.updateQueueTokenExpired(request.tokenId());
    }
}
