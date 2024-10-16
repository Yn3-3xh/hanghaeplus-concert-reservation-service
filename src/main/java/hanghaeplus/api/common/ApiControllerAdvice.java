package hanghaeplus.api.common;

import hanghaeplus.api.common.error.ApiErrorCode;
import hanghaeplus.api.common.error.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ApiErrorResponse(ApiErrorCode.SERVER_ERROR));
    }
}