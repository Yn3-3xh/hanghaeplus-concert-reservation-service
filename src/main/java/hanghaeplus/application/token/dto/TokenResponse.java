package hanghaeplus.application.token.dto;

import java.util.UUID;

public class TokenResponse {

    public record EnrollToken(
            UUID id
    ) {
    }
}
