package hanghaeplus.application.concert.error;

import hanghaeplus.domain.common.error.ErrorCode;
import hanghaeplus.domain.common.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import static hanghaeplus.domain.common.error.ErrorStatus.CONFLICT;
import static hanghaeplus.domain.common.error.ErrorStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCode {
    NOT_AVAILABLE_SEAT(CONFLICT, "예약 할 수 없는 좌석 입니다.", LogLevel.WARN),
    NOT_FOUND_AVAILABLE_RESERVATION(NOT_FOUND, "예약 정보가 존재하지 않습니다.", LogLevel.INFO),
    NOT_FOUND_SEAT(NOT_FOUND, "좌석 정보가 존재하지 않습니다.", LogLevel.INFO),
    NOT_FOUND_EXPIRED_RESERVATION(NOT_FOUND, "만료될 예약 정보가 존재하지 않습니다.", LogLevel.INFO),
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
