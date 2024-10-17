package hanghaeplus.application.order.service;

import hanghaeplus.domain.order.entity.Order;
import hanghaeplus.domain.order.entity.enums.OrderStatus;
import hanghaeplus.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

import static hanghaeplus.application.order.error.OrderErrorCode.NOT_FOUND_AVAILABLE_ORDER;

@Component
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository orderRepository;

    public void updateOrderCompleted(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_AVAILABLE_ORDER.getMessage()));
        order.updateStatus(OrderStatus.COMPLETED);

        orderRepository.saveOrder(order);
    }
}
