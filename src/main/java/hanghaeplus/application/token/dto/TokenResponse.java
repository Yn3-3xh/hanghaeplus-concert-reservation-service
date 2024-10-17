package hanghaeplus.application.token.dto;

public class TokenResponse {

    public record EnrollToken (
            String tokenId
    ) {
    }
}
