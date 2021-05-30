package com.pegasus.messengerserver.projection;

import java.time.LocalDateTime;

public interface MessageProjection extends EntityProjection {

  String getContent();

}
