package com.schooldevops.apifirst.openapi.domain;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TokenResponse
 */
@lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-11T03:52:49.082651+09:00[Asia/Seoul]")
public class TokenResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID token;

  public TokenResponse token(UUID token) {
    this.token = token;
    return this;
  }

  /**
   * Get token
   * @return token
  */
  @Valid 
  @Schema(name = "token", example = "21113572-4cb4-44ef-8e19-63269724b8be", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("token")
  public UUID getToken() {
    return token;
  }

  public void setToken(UUID token) {
    this.token = token;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TokenResponse tokenResponse = (TokenResponse) o;
    return Objects.equals(this.token, tokenResponse.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenResponse {\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

