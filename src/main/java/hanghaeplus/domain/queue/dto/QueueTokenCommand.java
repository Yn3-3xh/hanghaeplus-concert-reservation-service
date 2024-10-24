package hanghaeplus.domain.queue.dto;

public class QueueTokenCommand {

    public record CreateQueueTokenExpired (
            String tokenId
    ) {

    }
}
