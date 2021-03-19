package com.snappacking.osworks.domain.exception;

public class NegocioException extends RuntimeException{
  private static final long serialVersionUid = 1L;

  public NegocioException(String message){
    super(message);
  }

}
