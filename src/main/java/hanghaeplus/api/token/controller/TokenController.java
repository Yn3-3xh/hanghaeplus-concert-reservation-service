package hanghaeplus.api.token.controller;

import com.schooldevops.apifirst.openapi.domain.TokenRequest;
import com.schooldevops.apifirst.openapi.domain.TokenResponse;
import com.schooldevops.apifirst.openapi.rest.TokensApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TokenController implements TokensApi {
    @Override
    public ResponseEntity<TokenResponse> enrollToken(UUID X_USER_TOKEN, TokenRequest tokenRequest) {
        return ResponseEntity.ok(new TokenResponse(UUID.randomUUID()));
    }
}
