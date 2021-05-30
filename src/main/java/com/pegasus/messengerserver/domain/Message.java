package com.pegasus.messengerserver.domain;

import com.pegasus.messengerserver.projection.MessageProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message extends AbstractEntity implements Serializable, MessageProjection {

  private static final long serialVersionUID = 1L;

  private String content;

}
