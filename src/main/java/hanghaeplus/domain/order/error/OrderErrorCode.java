package hanghaeplus.domain.order.error;

import hanghaeplus.domain.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    INVALID_ORDER_ID("유효하지 않은 주문ID 입니다."),
    INVALID_USER_ID("유효하지 않은 유저ID 입니다."),
    INVALID_AMOUNT("유효하지 않은 금액입니다."),
    ;

    private final String message;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
