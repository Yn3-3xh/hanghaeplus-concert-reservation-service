package hanghaeplus.domain.token.entity;

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
public class Token {

    @Id
    private UUID id;

    private Long userId;

    private LocalDateTime expiredAt;

    public static Token create(UUID id, Long userId, LocalDateTime expiredAt) {
        return new Token(id , userId, expiredAt);
    }
}
