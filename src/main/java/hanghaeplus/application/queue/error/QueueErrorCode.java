package hanghaeplus.application.queue.error;

import hanghaeplus.application.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QueueErrorCode implements ErrorCode {

    NO_SUCH_CONCERT_QUEUE("콘서트의 대기열이 존재하지 않습니다."),
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
