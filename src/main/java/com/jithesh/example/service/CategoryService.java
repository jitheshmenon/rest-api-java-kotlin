package com.jithesh.example.service;

import com.jithesh.example.model.Category;
import com.jithesh.example.model.TodoItem;
import java.util.List;

public interface CategoryService {

  List<Category> getAllCategories();

  Category getCategory(final int id);

  Category createCategory(final Category category);

  Category updateCategory(final int id, final Category category);

  void deleteCategory(final int id);

  List<TodoItem> getCategoryTodoList(final int id);
}
