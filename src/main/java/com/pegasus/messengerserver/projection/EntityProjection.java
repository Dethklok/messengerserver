package com.pegasus.messengerserver.projection;

import java.time.LocalDateTime;

public interface EntityProjection {

  Long getId();
  String getUserId();
  LocalDateTime getCreatedAt();
  LocalDateTime getUpdatedAt();

}
