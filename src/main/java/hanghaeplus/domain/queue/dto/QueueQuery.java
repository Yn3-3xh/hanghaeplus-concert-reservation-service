package hanghaeplus.domain.queue.dto;

public class QueueQuery {

    public record Create(
            Long concertId
    ) {
    }

    public record CreateTokenWaiting(
            String tokenId,
            Long queueId
    ) {
    }

    public record CreateToken(
            String tokenId
    ) {
    }

    public record CreateWaitingQueueToken(
            Long concertId,
            String tokenId
    ) {
    }

    public record CreateActiveQueueToken(
            Long concertId,
            String tokenId
    ) {
    }
}
