package hanghaeplus.api.common.error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ApiErrorCode implements ErrorCode {

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "에러가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
