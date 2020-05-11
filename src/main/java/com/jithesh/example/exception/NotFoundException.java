package com.jithesh.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends ApiException {

  public static final String NOT_FOUND = "NOT_FOUND";

  public NotFoundException(final String message,
      final Throwable cause) {
    super(message, NOT_FOUND, cause);
  }

  public static ApiExceptionBuilder builder() {
    return new ApiExceptionBuilder() {
      @Override
      public NotFoundException build() {
        return new NotFoundException(message, cause);
      }
    };
  }
}
