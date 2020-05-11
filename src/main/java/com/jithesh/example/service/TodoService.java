package com.jithesh.example.service;

import com.jithesh.example.model.TodoItem;
import java.util.List;

/**
 * Service interface for CRUD operations on To-do Items
 */
public interface TodoService {

  /**
   * Retrieve the list of all To-do records
   * @return List of TodoItem
   */
  List<TodoItem> getAllTodoItems();

  /**
   * Update a single to-do record
   * @param todoItem - Item to update
   * @return Updated TodoItem
   */
  TodoItem updateTodoItem(final int id, final TodoItem todoItem);

  /**
   * Mark the status of TodoItem as Completed
   * @param todoItem - Item to mark as completed
   * @return modified TodoItem
   */
  TodoItem markItemAsCompleted(final int todoItem);

  /**
   * Clear all completed to-do Items
   */
  void clearCompleted();

  /**
   * Delete the given TodoItem from the repository
   * @param todoItem - - Item to be deleted
   */
  void deleteTodoItem(final int todoItem);

  /**
   * Update all items to completed status
   */
  void updateAllTodoAsCompleted();

  /**
   * Add a new to-do item to the repository
   * @param todoItem - Item to be newly created
   * @return the newly added TodoItem
   */
  TodoItem addTodoItem(final TodoItem todoItem);
}
