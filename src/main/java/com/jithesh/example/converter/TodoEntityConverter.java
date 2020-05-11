package com.jithesh.example.converter;

import com.jithesh.example.entity.Todo;
import com.jithesh.example.model.TodoItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * This will convert the TodoItem model from presentation layer to the
 * entity model for persistence
 */
@Component
public class TodoEntityConverter implements Converter<TodoItem, Todo> {

  /**
   * Converter method to set the item attributes to the entity class
   * The id is not expected to be set inside the converter as the ID will be
   * passed separately to identify an entity record.
   * @param todoItem - presentation model to be converted from
   * @return To-do Entity record
   */
  @Override
  public Todo convert(final TodoItem todoItem) {
    final Todo todo = new Todo();
    todo.setCompleted(todoItem.isCompleted());
    todo.setText(todoItem.getText());
    return todo;
  }
}
