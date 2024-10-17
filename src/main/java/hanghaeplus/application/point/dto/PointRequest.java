package hanghaeplus.application.point.dto;

public class PointRequest {

    public record PointSelection (
            Long userId
    ) {

    }

    public record PointCharge (
            Long userId,
            int amount
    ) {

    }
}
