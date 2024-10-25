package hanghaeplus.domain.token.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import hanghaeplus.domain.common.error.CoreException;
import hanghaeplus.domain.token.error.TokenErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static hanghaeplus.domain.token.entity.enums.TokenPolicy.TOKEN_EXPIRED_HOUR;

@Entity
@Table(name = "token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Token extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenId;

    private Long userId;

    private LocalDateTime expiredAt;

    public static Token create(String tokenId, Long userId) {
        return new Token(null, tokenId, userId, LocalDateTime.now().plusHours(TOKEN_EXPIRED_HOUR.getHour()));
    }

    public void checkExpired(LocalDateTime now) {
        if (this.expiredAt.isBefore(now)) {
            throw new CoreException(TokenErrorCode.EXPIRED_TOKEN);
        }
    }
}
