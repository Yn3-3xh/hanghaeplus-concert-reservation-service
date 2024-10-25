package hanghaeplus.domain.token.dto;

public class TokenCommand {

    public record Create(
            Long userId
    ) {
    }
}
