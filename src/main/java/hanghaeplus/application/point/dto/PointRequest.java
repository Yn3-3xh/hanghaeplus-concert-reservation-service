package hanghaeplus.application.point.dto;

public class PointRequest {

    public record PointSelection (
            String tokenId,
            Long userId
    ) {

    }

    public record PointCharge (
            String tokenId,
            Long userId,
            int amount
    ) {

    }
}
