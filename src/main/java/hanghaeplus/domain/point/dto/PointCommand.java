package hanghaeplus.domain.point.dto;

import hanghaeplus.domain.point.entity.enums.PointStatus;

public class PointCommand {

    public record Create(
            Long userId,
            int amount
    ) {
    }

    public record CreatePointHistory(
            Long userId,
            int amount,
            PointStatus status
    ) {
    }
}
