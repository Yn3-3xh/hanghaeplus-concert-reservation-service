package hanghaeplus.application.point.error;

import hanghaeplus.domain.common.error.ErrorCode;
import hanghaeplus.domain.common.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import static hanghaeplus.domain.common.error.ErrorStatus.CONFLICT;
import static hanghaeplus.domain.common.error.ErrorStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum PointErrorCode implements ErrorCode {

    NOT_FOUND_POINT(NOT_FOUND, "포인트 정보가 존재하지 않습니다.", LogLevel.INFO),
    INSUFFICIENT_POINTS(CONFLICT, "포인트가 부족합니다.", LogLevel.WARN),
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
