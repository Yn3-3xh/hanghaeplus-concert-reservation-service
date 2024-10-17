package hanghaeplus.application.concert.error;

import hanghaeplus.application.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCode {

    NOT_AVAILABLE_SEAT("예약 할 수 없는 좌석 입니다."),
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
