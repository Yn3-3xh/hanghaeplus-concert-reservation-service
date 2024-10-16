package hanghaeplus.application.token.dto;

public class TokenRequest {

    public record EnrollToken(
            Long userId
    ) {
    }
}
