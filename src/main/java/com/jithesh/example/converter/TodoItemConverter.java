package com.jithesh.example.converter;

import com.jithesh.example.entity.Todo;
import com.jithesh.example.model.Category;
import com.jithesh.example.model.TodoItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * This will convert the To-do Entity from persistence layer to the
 * presentation layer model
 */
@Component
public class TodoItemConverter implements Converter<Todo, TodoItem> {

  /**
   * Converts the entity model attributes to the presentation layer model
   * @param todoEntity - entity model tobe converted
   * @return TodoItem
   */
  @Override
  public TodoItem convert(final Todo todoEntity) {
    Category category = null;

    if (todoEntity.getCategory() != null) {
      category = Category.builder()
          .id(todoEntity.getCategory().getId())
          .name(todoEntity.getCategory().getName())
          .build();
    }

    return TodoItem.builder()
        .id(todoEntity.getId())
        .completed(todoEntity.isCompleted())
        .text(todoEntity.getText())
        .category(category)
        .build();
  }
}
