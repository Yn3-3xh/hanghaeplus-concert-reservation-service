package hanghaeplus.domain.order.dto;

import static hanghaeplus.domain.order.error.OrderErrorCode.INVALID_ORDER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OrderQuery 단위 테스트")
class OrderQueryUnitTest {

    @Test
    @DisplayName("OrderQuery.CreateAvailableOrder 성공")
    void pass_createAvailableOrderTest() {
        // given
        Long orderID = 1L;

        // when
        OrderQuery.CreateAvailableOrder query = new OrderQuery.CreateAvailableOrder(orderID);

        // then
        assertThat(query).isNotNull();
    }

    @Test
    @DisplayName("OrderQuery.CreateAvailableOrder 실패 - orderID가 null인 경우")
    void fail_createAvailableOrderTest1() {
        // given
        Long orderID = null;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderQuery.CreateAvailableOrder(orderID);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_ORDER_ID.getMessage());
    }

    @Test
    @DisplayName("OrderQuery.CreateAvailableOrder 실패 - orderID가 0 이하인 경우")
    void fail_createAvailableOrderTest2() {
        // given
        Long orderID = 0L;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderQuery.CreateAvailableOrder(orderID);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_ORDER_ID.getMessage());
    }
}
