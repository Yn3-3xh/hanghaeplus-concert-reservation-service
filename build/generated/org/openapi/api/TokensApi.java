/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.2.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapi.api;

import org.openapi.model.TokenHttpRequest;
import org.openapi.model.TokenHttpResponse;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-17T19:17:29.458938+09:00[Asia/Seoul]")
@Validated
@Tag(name = "tokens", description = "the tokens API")
public interface TokensApi {

    /**
     * POST /tokens : 토큰 발급 API
     *
     * @param tokenId  (optional)
     * @param tokenHttpRequest  (optional)
     * @return 새로 발급되거나 초기화된 사용자 인증 토큰 반환 (status code 200)
     */
    @Operation(
        operationId = "enrollToken",
        summary = "토큰 발급 API",
        responses = {
            @ApiResponse(responseCode = "200", description = "새로 발급되거나 초기화된 사용자 인증 토큰 반환", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = TokenHttpResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/tokens",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<TokenHttpResponse> enrollToken(
        @Parameter(name = "tokenId", description = "", in = ParameterIn.HEADER) @RequestHeader(value = "tokenId", required = false) String tokenId,
        @Parameter(name = "TokenHttpRequest", description = "") @Valid @RequestBody(required = false) TokenHttpRequest tokenHttpRequest
    );

}
