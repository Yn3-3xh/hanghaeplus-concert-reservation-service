package org.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
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
 * PointSelectHttpResponse
 */
@lombok.AllArgsConstructor @lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-17T17:09:25.125631+09:00[Asia/Seoul]")
public class PointSelectHttpResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private BigDecimal point;

  public PointSelectHttpResponse point(BigDecimal point) {
    this.point = point;
    return this;
  }

  /**
   * Get point
   * @return point
  */
  @Valid 
  @Schema(name = "point", example = "25000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("point")
  public BigDecimal getPoint() {
    return point;
  }

  public void setPoint(BigDecimal point) {
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
    PointSelectHttpResponse pointSelectHttpResponse = (PointSelectHttpResponse) o;
    return Objects.equals(this.point, pointSelectHttpResponse.point);
  }

  @Override
  public int hashCode() {
    return Objects.hash(point);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PointSelectHttpResponse {\n");
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

