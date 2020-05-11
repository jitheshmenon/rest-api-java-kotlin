package com.jithesh.example.service;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.jithesh.example.converter.TodoItemConverter;
import com.jithesh.example.entity.TodoCategory;
import com.jithesh.example.exception.BadRequestException;
import com.jithesh.example.exception.NotFoundException;
import com.jithesh.example.model.Category;
import com.jithesh.example.model.TodoItem;
import com.jithesh.example.repository.CategoryRepository;
import com.jithesh.example.exception.ExceptionMessageConstants;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  private CategoryRepository categoryRepository;

  private TodoItemConverter todoItemConverter;

  public CategoryServiceImpl(final CategoryRepository categoryRepository,
      final TodoItemConverter todoItemConverter) {
    this.categoryRepository = categoryRepository;
    this.todoItemConverter = todoItemConverter;
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll()
        .stream().filter(Objects::nonNull)
        .map(category ->
            Category.builder()
                .id(category.getId())
                .name(category.getName())
                .build())
        .collect(Collectors.toList());
  }

  @Override
  public Category getCategory(int id) {
    return
        categoryRepository.findById(id)
            .map(cat -> Category.builder().name(cat.getName()).id(cat.getId()).build())
        .orElseThrow(()->
            NotFoundException.builder()
            .message(ExceptionMessageConstants.CATEGORY_NOT_FOUND.getMessage())
            .build());
  }

  @Override
  public Category createCategory(final Category category) {
    final TodoCategory todoCategory =
      categoryRepository.save(
        TodoCategory.builder().name(category.getName()).build());
    category.setId(todoCategory.getId());
    return category;
  }

  @Override
  public Category updateCategory(final int id, final Category category) {
    Optional<TodoCategory> todoCategory =
      categoryRepository.findById(id);
    if (todoCategory.isPresent()) {
      todoCategory.get().setName(category.getName());
      categoryRepository.save(todoCategory.get());
      category.setId(id);
    } else {
      throw NotFoundException.builder()
          .message(ExceptionMessageConstants.CATEGORY_NOT_FOUND.getMessage())
          .build();
    }
    return category;
  }

  @Override
  public void deleteCategory(final int id) {
    Optional<TodoCategory> todoCategory = categoryRepository.findById(id);
    if (todoCategory.isPresent()) {
      if (!isEmpty(todoCategory.get().getTodoList())) {
        throw BadRequestException.builder()
            .message(ExceptionMessageConstants.ITEMS_EXISTS.getMessage())
            .build();
      }
      categoryRepository.deleteById(id);
    } else {
      throw NotFoundException.builder()
          .message(ExceptionMessageConstants.CATEGORY_NOT_FOUND.getMessage())
          .build();
    }
  }

  @Override
  public List<TodoItem> getCategoryTodoList(int id) {
    final TodoCategory category = categoryRepository.findCategoryById(id);
    if (category != null && !isEmpty(category.getTodoList())) {
      return category.getTodoList().stream()
          .map(todoItemConverter::convert).collect(Collectors.toList());
    } else {
      throw NotFoundException.builder()
          .message(ExceptionMessageConstants.EMPTY_CATEGORY.getMessage())
          .build();
    }
  }
}
