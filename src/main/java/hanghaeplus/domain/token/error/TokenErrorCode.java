package hanghaeplus.domain.token.error;

import hanghaeplus.domain.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    INVALID_USER_ID("유효하지 않은 사용자ID 입니다."),
    INVALID_TOKEN_ID("유효하지 않은 토큰ID 입니다."),
    EXPIRED_TOKEN("토큰이 만료되었습니다."),
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
