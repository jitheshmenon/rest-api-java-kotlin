package com.jithesh.example.service;

import com.jithesh.example.converter.TodoEntityConverter;
import com.jithesh.example.converter.TodoItemConverter;
import com.jithesh.example.entity.Todo;
import com.jithesh.example.exception.NotFoundException;
import com.jithesh.example.model.TodoItem;
import com.jithesh.example.repository.CategoryRepository;
import com.jithesh.example.repository.TodoRepository;
import com.jithesh.example.exception.ExceptionMessageConstants;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

  private static final Predicate<Todo> completedTodo = Todo::isCompleted;

  private TodoRepository todoRepository;

  private CategoryRepository categoryRepository;

  private TodoItemConverter todoItemConverter;

  private TodoEntityConverter todoEntityConverter;

  public TodoServiceImpl(
      final TodoRepository todoRepository,
      final CategoryRepository categoryRepository,
      final TodoItemConverter todoItemConverter,
      final TodoEntityConverter todoEntityConverter) {
    this.todoRepository = todoRepository;
    this.categoryRepository = categoryRepository;
    this.todoItemConverter = todoItemConverter;
    this.todoEntityConverter = todoEntityConverter;
  }

  @Override
  public TodoItem addTodoItem(final TodoItem todoItem) {
    final Todo todo =  todoEntityConverter.convert(todoItem);
    if (todoItem.getCategory() != null) {
          categoryRepository.findById(todoItem.getCategory().getId())
              .ifPresent(todo::setCategory);
    }
    return todoItemConverter.convert(todoRepository.save(todo));
  }

  @Override
  public void clearCompleted() {
    final List<Todo> markedForDeletion = todoRepository.findAll().stream()
        .filter(Objects::nonNull)
        .filter(completedTodo)
        .peek(todo -> todo.setCompleted(true))
        .collect(Collectors.toList());

    todoRepository.deleteAll(markedForDeletion);
  }

  @Override
  public TodoItem markItemAsCompleted(final int id) {
    final Optional<Todo> todo = todoRepository.findById(id);
    if (todo.isPresent()) {
      todo.get().setCompleted(true);
      todoRepository.save(todo.get());
      return todoItemConverter.convert(todo.get());
    } else {
      throw NotFoundException.builder()
          .message(ExceptionMessageConstants.TODO_ITEM_NOT_FOUND.getMessage())
          .build();
    }
  }

  @Override
  public TodoItem updateTodoItem(final int id, final TodoItem todoItem) {
    final Optional<Todo> todo = todoRepository.findById(id);
    if (todo.isPresent()) {
      todo.get().setCompleted(todoItem.isCompleted());
      todo.get().setText(todoItem.getText());
      todoRepository.save(todo.get());
      return todoItemConverter.convert(todo.get());
    } else {
      throw NotFoundException.builder()
          .message(ExceptionMessageConstants.TODO_ITEM_NOT_FOUND.getMessage())
          .build();
    }
  }

  @Override
  public void deleteTodoItem(final int id) {
    final Optional<Todo> todo = todoRepository.findById(id);
    if (todo.isPresent()) {
      todoRepository.deleteById(id);
    } else {
      throw NotFoundException.builder()
          .message(ExceptionMessageConstants.TODO_ITEM_NOT_FOUND.getMessage())
          .build();
    }
  }

  @Override
  public void updateAllTodoAsCompleted() {

    final List<Todo> markedForCompletion = todoRepository.findAll().stream()
        .filter(Objects::nonNull)
        .filter(completedTodo.negate())
        .peek(todo -> todo.setCompleted(true))
        .collect(Collectors.toList());
    todoRepository.saveAll(markedForCompletion);
  }

  @Override
  public List<TodoItem> getAllTodoItems() {
    return todoRepository.findAll().stream()
        .filter(Objects::nonNull)
        .map(todo -> todoItemConverter.convert(todo)).collect(Collectors.toList());
  }
}
