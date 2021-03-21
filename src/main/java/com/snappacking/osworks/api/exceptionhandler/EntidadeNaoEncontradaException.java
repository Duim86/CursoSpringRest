package com.snappacking.osworks.api.exceptionhandler;

import com.snappacking.osworks.domain.exception.NegocioException;

public class EntidadeNaoEncontradaException extends NegocioException {

  private static final long serialVersionUID = 1L;

  public EntidadeNaoEncontradaException(String message) {
    super(message);
  }
}
