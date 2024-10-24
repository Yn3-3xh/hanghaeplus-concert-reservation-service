package hanghaeplus.domain.queue.dto;

public class QueueCommand {

    public record CreateTokenWaiting(
            String tokenId,
            Long queueId
    ) {

    }
}
