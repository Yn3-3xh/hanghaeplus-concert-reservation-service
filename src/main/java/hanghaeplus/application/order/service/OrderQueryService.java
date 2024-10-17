package hanghaeplus.application.order.service;

import hanghaeplus.domain.order.dto.OrderQuery;
import hanghaeplus.domain.order.entity.Order;
import hanghaeplus.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static hanghaeplus.application.order.error.OrderErrorCode.NOT_FOUND_AVAILABLE_ORDER;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public Order getAvailableOrder(OrderQuery.CreateAvailableOrder query) {
        return orderRepository.findAvailableOrderById(query.orderID())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_AVAILABLE_ORDER.getMessage()));
    }
}
