package hanghaeplus.domain.token.error;

import hanghaeplus.domain.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    INVALID_USER_ID("유효하지 않은 사용자ID 입니다.");

    private final String message;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
