package com.pegasus.messengerserver.projection;

import java.time.LocalDateTime;

public interface EntityProjection {

  Long getId();
  LocalDateTime getCreatedAt();
  LocalDateTime getUpdatedAt();

}
