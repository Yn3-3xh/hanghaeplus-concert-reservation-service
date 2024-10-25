package hanghaeplus.application.order.service;

import hanghaeplus.application.order.error.OrderErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.order.dto.OrderCommand;
import hanghaeplus.domain.order.entity.Order;
import hanghaeplus.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository orderRepository;

    public void updateOrderCompleted(OrderCommand.CreateOrderCompleted command) {
        Order order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new CoreException(OrderErrorCode.NOT_FOUND_AVAILABLE_ORDER));
        order.updateCompleted();

        orderRepository.saveOrder(order);
    }
}
