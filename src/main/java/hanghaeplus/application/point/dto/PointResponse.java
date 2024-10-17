package hanghaeplus.application.point.dto;

import java.math.BigDecimal;

public class PointResponse {

    public record PointSelection (
            BigDecimal point
    ) {

    }
}
