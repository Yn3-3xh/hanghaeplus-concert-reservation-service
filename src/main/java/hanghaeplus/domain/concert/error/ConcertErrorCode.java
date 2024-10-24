package hanghaeplus.domain.concert.error;

import hanghaeplus.domain.common.error.ErrorCode;
import hanghaeplus.domain.common.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import static hanghaeplus.domain.common.error.ErrorStatus.BAD_REQUEST;

@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCode {

    INVALID_CONCERT_ID(BAD_REQUEST, "유효하지 않은 콘서트ID 입니다.", LogLevel.WARN),
    INVALID_CONCERT_DETAIL_ID(BAD_REQUEST, "유효하지 않은 콘서트 세부사항 ID 입니다.", LogLevel.WARN),
    INVALID_SEAT_ID(BAD_REQUEST, "유효하지 않은 콘서트 세부사항 ID 입니다.", LogLevel.WARN),
    INVALID_USER_ID(BAD_REQUEST, "유효하지 않은 유저ID 입니다.", LogLevel.WARN),
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