package hanghaeplus.application.point.error;

import hanghaeplus.domain.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PointErrorCode implements ErrorCode {

    NOT_FOUND_POINT("포인트 정보가 존재하지 않습니다."),
    INSUFFICIENT_POINTS("포인트가 부족합니다."),
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
