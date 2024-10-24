package hanghaeplus.domain.order.error;

import hanghaeplus.domain.common.error.ErrorCode;
import hanghaeplus.domain.common.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import static hanghaeplus.domain.common.error.ErrorStatus.BAD_REQUEST;

@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    INVALID_ORDER_ID(BAD_REQUEST, "유효하지 않은 주문ID 입니다.", LogLevel.WARN),
    INVALID_USER_ID(BAD_REQUEST, "유효하지 않은 유저ID 입니다.", LogLevel.WARN),
    INVALID_AMOUNT(BAD_REQUEST, "유효하지 않은 금액입니다.", LogLevel.WARN),
    ;

    private final ErrorStatus status;
    private final String message;
    private final LogLevel logLevel;

    @Override
    public ErrorStatus getStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public LogLevel getLogLevel() {
        return this.logLevel;
    }
}
