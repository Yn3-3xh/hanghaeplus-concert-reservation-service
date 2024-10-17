package hanghaeplus.application.order.error;

import hanghaeplus.application.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    NOT_FOUND_AVAILABLE_ORDER("주문 가능한 정보가 존재하지 않습니다."),
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
