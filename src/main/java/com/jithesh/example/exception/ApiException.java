package com.jithesh.example.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

  private final String errorCode;

  public ApiException(
      final String message,
      final String errorCode,
      final Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public static <B extends ApiExceptionBuilder> B builder() {
    return (B) new ApiExceptionBuilder();
  }

  public static class ApiExceptionBuilder<B extends ApiExceptionBuilder> {

    protected String message;
    protected String errorCode;
    protected Throwable cause;

    protected ApiExceptionBuilder() {
    }

    public B message(final String message) {
      this.message = message;
      return (B)this;
    }

    public B errorCode(final String errorCode) {
      this.errorCode = errorCode;
      return (B)this;
    }

    public B cause(final Throwable cause) {
      this.cause = cause;
      return (B)this;
    }

    public ApiException build() {
      return new ApiException(message, errorCode, cause);
    }
  }
}
