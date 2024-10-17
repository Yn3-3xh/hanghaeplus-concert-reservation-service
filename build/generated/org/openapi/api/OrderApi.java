/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.2.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapi.api;

import org.openapi.model.PaymentHttpRequest;
import org.openapi.model.PaymentHttpResponse;
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
@Tag(name = "order", description = "the order API")
public interface OrderApi {

    /**
     * POST /order/payments : 결제 API
     *
     * @param tokenId  (optional)
     * @param paymentHttpRequest  (optional)
     * @return 결제 완료 (status code 200)
     */
    @Operation(
        operationId = "executePayment",
        summary = "결제 API",
        responses = {
            @ApiResponse(responseCode = "200", description = "결제 완료", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentHttpResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/order/payments",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<PaymentHttpResponse> executePayment(
        @Parameter(name = "tokenId", description = "", in = ParameterIn.HEADER) @RequestHeader(value = "tokenId", required = false) String tokenId,
        @Parameter(name = "PaymentHttpRequest", description = "") @Valid @RequestBody(required = false) PaymentHttpRequest paymentHttpRequest
    );

}
