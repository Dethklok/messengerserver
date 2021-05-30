package com.pegasus.messengerserver.dto;

import com.pegasus.messengerserver.projection.EntityProjection;
import lombok.*;

import java.time.LocalDateTime;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractResponseDto implements EntityProjection {

  protected Long id;
  protected String userId;
  protected LocalDateTime createdAt;
  protected LocalDateTime updatedAt;

}
