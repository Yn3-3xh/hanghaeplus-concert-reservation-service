package hanghaeplus.domain.queue.dto;

public class QueueTokenCommand {

    public record CreateQueueTokenExpired(
            String tokenId
    ) {

    }

    public record CreateQueueTokenDelete(
            Long queueId,
            String tokenId
    ) {

    }
}
