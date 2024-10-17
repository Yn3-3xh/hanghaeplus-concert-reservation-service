package hanghaeplus.application.concert.error;

import hanghaeplus.application.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConcertErrorCode implements ErrorCode {

    NOT_AVAILABLE_SEAT("예약 할 수 없는 좌석 입니다."),
    NOT_FOUND_AVAILABLE_RESERVATION("예약 정보가 존재하지 않습니다."),
    NOT_FOUND_SEAT("좌석 정보가 존재하지 않습니다."),
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
