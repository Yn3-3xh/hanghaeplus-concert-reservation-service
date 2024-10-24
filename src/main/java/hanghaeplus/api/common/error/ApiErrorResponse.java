package hanghaeplus.api.common.error;

public record ApiErrorResponse(
        int code,
        String message
) {
}