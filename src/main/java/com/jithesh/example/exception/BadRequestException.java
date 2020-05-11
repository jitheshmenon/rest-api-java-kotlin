package com.jithesh.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends ApiException {

  private static final String BAD_REQUEST = "BAD_REQUEST";

  public BadRequestException(final String message,
      final Throwable cause) {
    super(message, BAD_REQUEST, cause);
  }

  public static ApiExceptionBuilder builder() {
    return new ApiExceptionBuilder() {
      @Override
      public BadRequestException build() {
        return new BadRequestException(message, cause);
      }
    };
  }
}
