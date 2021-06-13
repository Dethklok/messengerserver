package com.pegasus.messengerserver.dto;

import com.pegasus.messengerserver.projection.MessageProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PROTECTED)
public class MessageDto extends AbstractResponseDto {

  private String content;

  public MessageDto(MessageProjection projection) {
    this.id = projection.getId();
    this.content = projection.getContent();
    this.userId = projection.getUserId();
    this.createdAt = projection.getCreatedAt();
    this.updatedAt = projection.getUpdatedAt();
  }

}
