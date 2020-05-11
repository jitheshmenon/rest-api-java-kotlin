package com.jithesh.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import java.util.List;
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
public class Category {

  @ApiModelProperty(value = "Unique identifier for a category", example="1", accessMode = AccessMode.READ_ONLY)
  private Integer id;

  @NotBlank
  @ApiModelProperty(value = "Category name", example = "bills to pay")
  private String name;

  @ApiModelProperty(value = "List of TODO items tagged to the category")
  private List<TodoItem> todoItemList;
}
