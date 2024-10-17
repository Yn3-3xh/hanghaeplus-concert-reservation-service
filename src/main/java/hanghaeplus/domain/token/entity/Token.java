package hanghaeplus.domain.token.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static hanghaeplus.domain.token.error.TokenErrorCode.EXPIRED_TOKEN;

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

    public static Token create(String tokenId, Long userId, LocalDateTime expiredAt) {
        return new Token(null, tokenId, userId, expiredAt);
    }

    public void checkExpired() {
        if (this.expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException(EXPIRED_TOKEN.getMessage());
        }
    }
}
