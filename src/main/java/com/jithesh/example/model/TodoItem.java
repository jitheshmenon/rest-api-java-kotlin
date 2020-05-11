package com.jithesh.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoItem {

  @ApiModelProperty(value = "Unique identifier for a TODO item", example="1", accessMode = AccessMode.READ_ONLY)
  private Integer id;

  @NotBlank
  @ApiModelProperty(value = "TODO text content", example = "Use React")
  private String text;

  @ApiModelProperty(value = "Status of TODO item", example = "false")
  private boolean completed = false;

  @ApiModelProperty(value = "Selected category of the TODO item", accessMode = AccessMode.READ_ONLY)
  private Category category;
}
