package hanghaeplus.application.order.service;

import hanghaeplus.application.order.error.OrderErrorCode;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.order.dto.OrderQuery;
import hanghaeplus.domain.order.entity.Order;
import hanghaeplus.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public Order getAvailableOrder(OrderQuery.CreateAvailableOrder query) {
        return orderRepository.findAvailableOrderById(query.orderID())
                .orElseThrow(() -> new CoreException(OrderErrorCode.NOT_FOUND_AVAILABLE_ORDER));
    }
}
