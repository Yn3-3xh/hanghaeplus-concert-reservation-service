package hanghaeplus.api.token.controller;

import hanghaeplus.api.token.mapper.TokenMapper;
import hanghaeplus.application.token.dto.TokenRequest;
import hanghaeplus.application.token.facade.TokenFacade;
import lombok.RequiredArgsConstructor;
import org.openapi.api.TokensApi;
import org.openapi.model.TokenHttpRequest;
import org.openapi.model.TokenHttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController implements TokensApi {

    private final TokenFacade tokenFacade;
    private final TokenMapper tokenMapper;

    @Override
    public ResponseEntity<TokenHttpResponse> enrollToken(String tokenId, TokenHttpRequest tokenHttpRequest) {
        TokenRequest.EnrollToken request = tokenMapper.toDto(tokenHttpRequest);
        return ResponseEntity.ok(
                tokenMapper.fromDto(tokenFacade.generateToken(request)));
    }
}