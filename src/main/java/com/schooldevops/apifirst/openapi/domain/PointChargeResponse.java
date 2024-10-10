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
 * PointChargeResponse
 */
@lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-11T03:52:49.082651+09:00[Asia/Seoul]")
public class PointChargeResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer point;

  public PointChargeResponse point(Integer point) {
    this.point = point;
    return this;
  }

  /**
   * Get point
   * @return point
  */
  
  @Schema(name = "point", example = "25000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("point")
  public Integer getPoint() {
    return point;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PointChargeResponse pointChargeResponse = (PointChargeResponse) o;
    return Objects.equals(this.point, pointChargeResponse.point);
  }

  @Override
  public int hashCode() {
    return Objects.hash(point);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PointChargeResponse {\n");
    sb.append("    point: ").append(toIndentedString(point)).append("\n");
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

