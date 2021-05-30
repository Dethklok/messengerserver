package com.pegasus.messengerserver.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter(AccessLevel.PROTECTED)
@Getter(AccessLevel.PUBLIC)
public class SaveMessageDto {

  String content;

}
