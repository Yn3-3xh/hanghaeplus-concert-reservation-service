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
 * AvailableSeatsHttpResponse
 */
@lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-18T07:00:19.556575+09:00[Asia/Seoul]")
public class AvailableSeatsHttpResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long seatId;

  private String seatName;

  private Integer price;

  public AvailableSeatsHttpResponse seatId(Long seatId) {
    this.seatId = seatId;
    return this;
  }

  /**
   * Get seatId
   * @return seatId
  */
  
  @Schema(name = "seatId", example = "3", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("seatId")
  public Long getSeatId() {
    return seatId;
  }

  public void setSeatId(Long seatId) {
    this.seatId = seatId;
  }

  public AvailableSeatsHttpResponse seatName(String seatName) {
    this.seatName = seatName;
    return this;
  }

  /**
   * Get seatName
   * @return seatName
  */
  
  @Schema(name = "seatName", example = "A-3", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("seatName")
  public String getSeatName() {
    return seatName;
  }

  public void setSeatName(String seatName) {
    this.seatName = seatName;
  }

  public AvailableSeatsHttpResponse price(Integer price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  */
  
  @Schema(name = "price", example = "15000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AvailableSeatsHttpResponse availableSeatsHttpResponse = (AvailableSeatsHttpResponse) o;
    return Objects.equals(this.seatId, availableSeatsHttpResponse.seatId) &&
        Objects.equals(this.seatName, availableSeatsHttpResponse.seatName) &&
        Objects.equals(this.price, availableSeatsHttpResponse.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seatId, seatName, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvailableSeatsHttpResponse {\n");
    sb.append("    seatId: ").append(toIndentedString(seatId)).append("\n");
    sb.append("    seatName: ").append(toIndentedString(seatName)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

