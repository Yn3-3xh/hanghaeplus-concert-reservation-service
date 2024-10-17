package hanghaeplus.domain.point.error;

import hanghaeplus.domain.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PointErrorCode implements ErrorCode {

    INVALID_USER_ID("유효하지 않은 유저ID 입니다."),
    INVALID_AMOUNT("유효하지 않은 금액입니다."),
    INVALID_STATUS("유효하지 않은 상태값입니다."),
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
