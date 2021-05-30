package com.pegasus.messengerserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFound extends RuntimeException {

  public EntityNotFound(Long id, String entityName) {
    super(entityName + " with id " + id + " is not found");
  }

}
