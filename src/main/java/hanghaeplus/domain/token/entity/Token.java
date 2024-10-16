package hanghaeplus.domain.token.entity;

import hanghaeplus.domain.common.AbstractAuditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Token extends AbstractAuditable {

    @Id
    private String id;

    private Long userId;

    private LocalDateTime expiredAt;

    public static Token create(String id, Long userId, LocalDateTime expiredAt) {
        return new Token(id, userId, expiredAt);
    }
}
