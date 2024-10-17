package hanghaeplus.domain.point.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.point.error.PointErrorCode.INVALID_AMOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        Point point = new Point(pointId, userId, hasPoint);

        // when
        point.charge(amount);

        // then
        assertThat(point.getPoint()).isEqualTo(hasPoint + amount);
    }

    @Test
    @DisplayName("charge 실패 - 금액이 0 이하인 경우")
    void fail_charge() {
        // given
        Long pointId = 1L;
        Long userId = 1L;
        int hasPoint = 1000;
        int amount = 0;
        Point point = new Point(pointId, userId, hasPoint);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            point.charge(amount);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_AMOUNT.getMessage());
    }
}
