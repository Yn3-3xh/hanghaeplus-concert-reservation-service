package com.schooldevops.apifirst.openapi.domain;

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
 * PaymentRequest
 */
@lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-11T03:52:49.082651+09:00[Asia/Seoul]")
public class PaymentRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer userId;

  private Integer concertId;

  private Integer seatId;

  private Integer amount;

  public PaymentRequest userId(Integer userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  
  @Schema(name = "userId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userId")
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public PaymentRequest concertId(Integer concertId) {
    this.concertId = concertId;
    return this;
  }

  /**
   * Get concertId
   * @return concertId
  */
  
  @Schema(name = "concertId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("concertId")
  public Integer getConcertId() {
    return concertId;
  }

  public void setConcertId(Integer concertId) {
    this.concertId = concertId;
  }

  public PaymentRequest seatId(Integer seatId) {
    this.seatId = seatId;
    return this;
  }

  /**
   * Get seatId
   * @return seatId
  */
  
  @Schema(name = "seatId", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("seatId")
  public Integer getSeatId() {
    return seatId;
  }

  public void setSeatId(Integer seatId) {
    this.seatId = seatId;
  }

  public PaymentRequest amount(Integer amount) {
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
    PaymentRequest paymentRequest = (PaymentRequest) o;
    return Objects.equals(this.userId, paymentRequest.userId) &&
        Objects.equals(this.concertId, paymentRequest.concertId) &&
        Objects.equals(this.seatId, paymentRequest.seatId) &&
        Objects.equals(this.amount, paymentRequest.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, concertId, seatId, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentRequest {\n");
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

