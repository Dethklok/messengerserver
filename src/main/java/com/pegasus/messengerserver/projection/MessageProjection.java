package com.pegasus.messengerserver.projection;

public interface MessageProjection extends EntityProjection {

  String getContent();
  String getUserId();

}
