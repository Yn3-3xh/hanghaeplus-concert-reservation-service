package com.schooldevops.apifirst.openapi.domain;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
 * AvailableSeatsResponseInner
 */
@lombok.AllArgsConstructor @lombok.NoArgsConstructor

@JsonTypeName("AvailableSeatsResponse_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-11T03:52:49.082651+09:00[Asia/Seoul]")
public class AvailableSeatsResponseInner implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer seatId;

  private String seatName;

  public AvailableSeatsResponseInner seatId(Integer seatId) {
    this.seatId = seatId;
    return this;
  }

  /**
   * Get seatId
   * @return seatId
  */
  
  @Schema(name = "seatId", example = "3", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("seatId")
  public Integer getSeatId() {
    return seatId;
  }

  public void setSeatId(Integer seatId) {
    this.seatId = seatId;
  }

  public AvailableSeatsResponseInner seatName(String seatName) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AvailableSeatsResponseInner availableSeatsResponseInner = (AvailableSeatsResponseInner) o;
    return Objects.equals(this.seatId, availableSeatsResponseInner.seatId) &&
        Objects.equals(this.seatName, availableSeatsResponseInner.seatName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seatId, seatName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvailableSeatsResponseInner {\n");
    sb.append("    seatId: ").append(toIndentedString(seatId)).append("\n");
    sb.append("    seatName: ").append(toIndentedString(seatName)).append("\n");
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

