package hanghaeplus.domain.token.error;

import hanghaeplus.domain.common.error.ErrorCode;
import hanghaeplus.domain.common.error.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import static hanghaeplus.domain.common.error.ErrorStatus.BAD_REQUEST;
import static hanghaeplus.domain.common.error.ErrorStatus.CONFLICT;

@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

    INVALID_USER_ID(BAD_REQUEST, "유효하지 않은 사용자ID 입니다.", LogLevel.WARN),
    INVALID_TOKEN_ID(BAD_REQUEST, "유효하지 않은 토큰ID 입니다.", LogLevel.WARN),
    EXPIRED_TOKEN(CONFLICT, "토큰이 만료되었습니다.", LogLevel.INFO),
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
