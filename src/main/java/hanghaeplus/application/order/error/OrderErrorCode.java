package hanghaeplus.application.order.error;

import hanghaeplus.domain.common.error.ErrorCode;
import hanghaeplus.domain.common.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import static hanghaeplus.domain.common.error.ErrorStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    NOT_FOUND_AVAILABLE_ORDER(NOT_FOUND, "주문 가능한 정보가 존재하지 않습니다.", LogLevel.INFO),
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
