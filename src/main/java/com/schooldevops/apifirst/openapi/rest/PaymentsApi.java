/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.2.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.schooldevops.apifirst.openapi.rest;

import com.schooldevops.apifirst.openapi.domain.PaymentRequest;
import com.schooldevops.apifirst.openapi.domain.PaymentResponse;
import java.util.UUID;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-11T03:52:49.082651+09:00[Asia/Seoul]")
@Validated
@Tag(name = "payments", description = "the payments API")
public interface PaymentsApi {

    /**
     * POST /payments : 결제 API
     *
     * @param X_USER_TOKEN  (optional)
     * @param paymentRequest  (optional)
     * @return 결제 완료 (status code 200)
     */
    @Operation(
        operationId = "processPayment",
        summary = "결제 API",
        responses = {
            @ApiResponse(responseCode = "200", description = "결제 완료", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/payments",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<PaymentResponse> processPayment(
        @Parameter(name = "X-USER-TOKEN", description = "", in = ParameterIn.HEADER) @RequestHeader(value = "X-USER-TOKEN", required = false) UUID X_USER_TOKEN,
        @Parameter(name = "PaymentRequest", description = "") @Valid @RequestBody(required = false) PaymentRequest paymentRequest
    );

}
