package hanghaeplus.domain.concert.error;

import hanghaeplus.domain.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCode {

    INVALID_CONCERT_ID("유효하지 않은 콘서트ID 입니다."),
    INVALID_CONCERT_DETAIL_ID("유효하지 않은 콘서트 세부사항 ID 입니다."),
    INVALID_SEAT_ID("유효하지 않은 콘서트 세부사항 ID 입니다."),
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