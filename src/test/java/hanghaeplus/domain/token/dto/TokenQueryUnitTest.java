package hanghaeplus.domain.token.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hanghaeplus.domain.token.error.TokenErrorCode.INVALID_TOKEN_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TokenQuery 단위 테스트")
class TokenQueryUnitTest {

    @Test
    @DisplayName("TokenQuery.Create 성공")
    void pass_createTest() {
        // given
        String tokenId = "UUID";

        // when
        TokenQuery.Create query = new TokenQuery.Create(tokenId);

        // then
        assertThat(query).isNotNull();
    }

    @Test
    @DisplayName("TokenQuery.Create 실패 - tokenId가 null인 경우")
    void fail_createTest1() {
        // given
        String tokenId = null;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TokenQuery.Create(tokenId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_TOKEN_ID.getMessage());
    }

    @Test
    @DisplayName("TokenQuery.Create 실패 - tokenId가 빈 문자열인 경우")
    void fail_createTest2() {
        // given
        String tokenId = "";

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TokenQuery.Create(tokenId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo(INVALID_TOKEN_ID.getMessage());
    }
}