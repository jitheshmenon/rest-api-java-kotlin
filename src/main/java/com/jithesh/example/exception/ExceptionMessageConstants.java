package com.jithesh.example.exception;

public enum ExceptionMessageConstants {

  TODO_ITEM_NOT_FOUND("To do item not found"),

  CATEGORY_NOT_FOUND ("Category not found"),

  EMPTY_CATEGORY("No todo items found in this category"),

  ITEMS_EXISTS ("Todo items exists in category, can not be deleted")
  ;


  private String message;

  ExceptionMessageConstants(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
