package org.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * PaymentHttpRequest
 */
@lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-17T06:25:33.385941+09:00[Asia/Seoul]")
public class PaymentHttpRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long userId;

  private Long concertId;

  private Long seatId;

  private Integer amount;

  public PaymentHttpRequest userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  
  @Schema(name = "userId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userId")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public PaymentHttpRequest concertId(Long concertId) {
    this.concertId = concertId;
    return this;
  }

  /**
   * Get concertId
   * @return concertId
  */
  
  @Schema(name = "concertId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("concertId")
  public Long getConcertId() {
    return concertId;
  }

  public void setConcertId(Long concertId) {
    this.concertId = concertId;
  }

  public PaymentHttpRequest seatId(Long seatId) {
    this.seatId = seatId;
    return this;
  }

  /**
   * Get seatId
   * @return seatId
  */
  
  @Schema(name = "seatId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("seatId")
  public Long getSeatId() {
    return seatId;
  }

  public void setSeatId(Long seatId) {
    this.seatId = seatId;
  }

  public PaymentHttpRequest amount(Integer amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  
  @Schema(name = "amount", example = "25000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("amount")
  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentHttpRequest paymentHttpRequest = (PaymentHttpRequest) o;
    return Objects.equals(this.userId, paymentHttpRequest.userId) &&
        Objects.equals(this.concertId, paymentHttpRequest.concertId) &&
        Objects.equals(this.seatId, paymentHttpRequest.seatId) &&
        Objects.equals(this.amount, paymentHttpRequest.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, concertId, seatId, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentHttpRequest {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    concertId: ").append(toIndentedString(concertId)).append("\n");
    sb.append("    seatId: ").append(toIndentedString(seatId)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

