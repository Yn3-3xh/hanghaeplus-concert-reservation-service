package hanghaeplus.domain.token.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenPolicy {

    TOKEN_EXPIRED_HOUR(1),
    ;

    private final int hour;
}
