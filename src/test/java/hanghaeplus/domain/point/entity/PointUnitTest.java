package hanghaeplus.domain.point.entity;

import hanghaeplus.domain.common.error.CoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hanghaeplus.application.point.error.PointErrorCode.INSUFFICIENT_POINTS;
import static hanghaeplus.domain.point.error.PointErrorCode.INVALID_AMOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Point 단위 테스트")
class PointUnitTest {

    @Test
    @DisplayName("charge 성공 - 유효한 금액")
    void pass_charge() {
        // given
        Long pointId = 1L;
        Long userId = 1L;
        int hasPoint = 1000;
        int amount = 500;
        Point sut = new Point(pointId, userId, hasPoint, null);

        // when
        sut.charge(amount);

        // then
        assertThat(sut.getPoint()).isEqualTo(hasPoint + amount);
    }

    @Test
    @DisplayName("charge 실패 - 금액이 0 이하인 경우")
    void fail_charge() {
        // given
        Long pointId = 1L;
        Long userId = 1L;
        int hasPoint = 1000;
        int amount = 0;
        Point point = new Point(pointId, userId, hasPoint, null);

        // when
        CoreException sut = assertThrows(CoreException.class, () -> {
            point.charge(amount);
        });

        // then
        assertThat(sut.getMessage()).isEqualTo(INVALID_AMOUNT.getMessage());
    }

    @Test
    @DisplayName("withdraw 성공 - 유효한 금액")
    void pass_withdraw() {
        // given
        Long pointId = 1L;
        Long userId = 1L;
        int hasPoint = 1000;
        int amount = 500;
        Point sut = new Point(pointId, userId, hasPoint, null);

        // when
        sut.withdraw(amount);

        // then
        assertThat(sut.getPoint()).isEqualTo(hasPoint - amount);
    }

    @Test
    @DisplayName("withdraw 실패 - 금액이 0 이하인 경우")
    void fail_withdraw_amountZeroOrLess() {
        // given
        Long pointId = 1L;
        Long userId = 1L;
        int hasPoint = 1000;
        int amount = 0;
        Point point = new Point(pointId, userId, hasPoint, null);

        // when
        CoreException sut = assertThrows(CoreException.class, () -> {
            point.withdraw(amount);
        });

        // then
        assertThat(sut.getMessage()).isEqualTo(INVALID_AMOUNT.getMessage());
    }

    @Test
    @DisplayName("withdraw 실패 - 포인트가 부족한 경우")
    void fail_withdraw_insufficientPoints() {
        // given
        Long pointId = 1L;
        Long userId = 1L;
        int hasPoint = 300;
        int amount = 500;
        Point point = new Point(pointId, userId, hasPoint, null);

        // when
        CoreException sut = assertThrows(CoreException.class, () -> {
            point.withdraw(amount);
        });

        // then
        assertThat(sut.getMessage()).isEqualTo(INSUFFICIENT_POINTS.getMessage());
    }
}
