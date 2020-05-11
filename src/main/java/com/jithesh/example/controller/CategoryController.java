package com.jithesh.example.controller;

import com.jithesh.example.model.Category;
import com.jithesh.example.model.TodoItem;
import com.jithesh.example.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@CrossOrigin
@Api(tags = "Todo Category")
public class CategoryController {

  CategoryService categoryService;

  public CategoryController(final CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping(path = "/categories", produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to retrieve all categories",
      notes = "This API endpoint returns list of all categories defined")
  public List<Category> list() {
    return categoryService.getAllCategories();
  }

  @PostMapping(path = "/category",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to create a new task category",
      notes = "This API endpoint accepts a task category and stores in the repository")
  @ResponseStatus(HttpStatus.CREATED)
  public Category create(@RequestBody Category category) {
    return categoryService.createCategory(category);
  }

  @DeleteMapping(path = "/category/{id}")
  @ApiOperation(value = "Endpoint to delete an existing task category",
      notes = "This API endpoint accepts a category ID and deletes the category from repository")
  public void delete(
      @ApiParam(value = "The unique category ID", required = true)
      @NotNull @PathVariable final Integer id) {
    categoryService.deleteCategory(id);
  }

  @PutMapping(path = "/category/{id}",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to update an existing category type",
      notes = "This API endpoint accepts a category ID and updates the record in repository")
  public Category update(
      @ApiParam(value = "The unique category ID", required = true)
      @NotNull @PathVariable final Integer id, @RequestBody Category category) {
    return categoryService.updateCategory(id, category);
  }

  @GetMapping(path = "/category/{id}/todos",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to get the list of all TODOs in the category",
      notes = "This API endpoint accepts a category ID returns the list of TODO items in that category")
  public List<TodoItem> getTodos(
      @ApiParam(value = "The unique category ID", required = true)
      @NotNull @PathVariable final Integer id) {
    return categoryService.getCategoryTodoList(id);
  }
}
