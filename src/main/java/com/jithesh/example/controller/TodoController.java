package com.jithesh.example.controller;

import com.jithesh.example.model.TodoItem;
import com.jithesh.example.service.TodoService;
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
@Api(tags = "Todo Items")
public class TodoController {

  private TodoService todoService;

  public TodoController(final TodoService todoService) {
    this.todoService = todoService;
  }

  @GetMapping(path = "/todos", produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to retrieve all TODO items",
      notes = "This API endpoint returns all TODO items")
  public List<TodoItem> list() {
    return todoService.getAllTodoItems();
  }

  @PostMapping(path = "/todo",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to create a new todo item",
      notes = "This API endpoint accepts a TODO item and stores in the repository")
  @ResponseStatus(HttpStatus.CREATED)
  public TodoItem create(@RequestBody TodoItem todo) {
    return todoService.addTodoItem(todo);
  }


  @DeleteMapping(path = "/todo/{id}")
  @ApiOperation(value = "Endpoint to delete an existing TODO item",
      notes = "This API endpoint accepts a TODO item ID and deletes the record from repository")
  public void delete(
      @ApiParam(value = "The unique id of TODO item", required = true, defaultValue = "1")
      @NotNull @PathVariable final Integer id) {
    todoService.deleteTodoItem(id);
  }

  @PutMapping(path = "/todo/{id}",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to update an existing todo item",
      notes = "This API endpoint accepts a TODO item ID and updates the record in repository")
  public TodoItem update(
      @ApiParam(value = "The unique id of TODO item", required = true, defaultValue = "1")
      @NotNull @PathVariable final Integer id, @RequestBody TodoItem todoItem) {
    return todoService.updateTodoItem(id, todoItem);
  }

  @DeleteMapping(path = "/todos/clear",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to delete all the completed entries",
      notes = "This API endpoint deletes all TODO entries with status as completed")
  public List<TodoItem> clearCompleted() {
    todoService.clearCompleted();
    return todoService.getAllTodoItems();
  }

  @PostMapping(path = "/todos/mark-all-complete",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to mark all TODO activities as completed",
      notes = "This API endpoint marks all TODO activities as complete")
  public List<TodoItem> completeAll() {
    todoService.updateAllTodoAsCompleted();
    return todoService.getAllTodoItems();
  }

  @PostMapping(path = "/todo/{id}/mark-complete",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Endpoint to mark a given TODO activity status as completed",
      notes = "This API endpoint marks status of TODO activity as complete")
  public TodoItem markAsComplete(
      @ApiParam(value = "The unique id of TODO item", required = true, defaultValue = "1")
      @NotNull @PathVariable final Integer id) {
    return todoService.markItemAsCompleted(id);
  }
}
