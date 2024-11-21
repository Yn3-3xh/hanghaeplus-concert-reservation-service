package hanghaeplus.domain.order.entity.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderOutboxStatus {

    INIT("이벤트 발행 전"),
    FAILED("이벤트 발행 실패"),
    PUBLISHED("이벤트 발행 성공"),
    ;

    private final String value;
}
