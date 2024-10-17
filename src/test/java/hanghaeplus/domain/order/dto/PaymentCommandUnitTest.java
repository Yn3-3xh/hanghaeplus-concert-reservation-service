package hanghaeplus.domain.order.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.order.error.OrderErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PaymentCommand 단위 테스트")
class PaymentCommandUnitTest {

    @Test
    @DisplayName("PaymentCommand.Create 성공")
    void pass_createTest() {
        // given
        Long userId = 1L;
        Long orderId = 1L;
        int amount = 100;

        // when
        PaymentCommand.Create command = new PaymentCommand.Create(userId, orderId, amount);

        // then
        assertThat(command).isNotNull();
    }

    @Test
    @DisplayName("PaymentCommand.Create 실패 - userId가 null인 경우")
    void fail_createTest1() {
        // given
        Long userId = null;
        Long orderId = 1L;
        int amount = 100;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new PaymentCommand.Create(userId, orderId, amount);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("PaymentCommand.Create 실패 - userId가 0 이하인 경우")
    void fail_createTest2() {
        // given
        Long userId = 0L;
        Long orderId = 1L;
        int amount = 100;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new PaymentCommand.Create(userId, orderId, amount);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("PaymentCommand.Create 실패 - orderId가 null인 경우")
    void fail_createTest3() {
        // given
        Long userId = 1L;
        Long orderId = null;
        int amount = 100;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new PaymentCommand.Create(userId, orderId, amount);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_ORDER_ID.getMessage());
    }

    @Test
    @DisplayName("PaymentCommand.Create 실패 - orderId가 0 이하인 경우")
    void fail_createTest4() {
        // given
        Long userId = 1L;
        Long orderId = 0L;
        int amount = 100;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new PaymentCommand.Create(userId, orderId, amount);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_ORDER_ID.getMessage());
    }

    @Test
    @DisplayName("PaymentCommand.Create 실패 - amount가 0 이하인 경우")
    void fail_createTest5() {
        // given
        Long userId = 1L;
        Long orderId = 1L;
        int amount = 0;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new PaymentCommand.Create(userId, orderId, amount);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_AMOUNT.getMessage());
    }
}
