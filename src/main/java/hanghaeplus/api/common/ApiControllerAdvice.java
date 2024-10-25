package hanghaeplus.api.common;

import hanghaeplus.api.common.error.ApiErrorResponse;
import hanghaeplus.domain.common.error.CoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        log.error("[ERROR] {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 에러가 발생했습니다."));
    }

    @ExceptionHandler(value = CoreException.class)
    public ResponseEntity<ApiErrorResponse> handleException(CoreException e) {
        switch (e.getErrorCode().getLogLevel()) {
            case ERROR -> log.error("[ERROR] {}", e.getMessage());
            case WARN -> log.warn("[WARNING] {}", e.getMessage());
            default -> log.info("[INFO] {}", e.getMessage());
        }

        HttpStatus httpStatus;
        switch (e.getErrorCode().getStatus()) {
            case BAD_REQUEST -> httpStatus = HttpStatus.BAD_REQUEST;
            case NOT_FOUND -> httpStatus = HttpStatus.NOT_FOUND;
            case CONFLICT -> httpStatus = HttpStatus.CONFLICT;
            default -> httpStatus = HttpStatus.OK;
        }

        return ResponseEntity
                .status(httpStatus)
                .body(new ApiErrorResponse(httpStatus.value(), e.getMessage()));
    }
}