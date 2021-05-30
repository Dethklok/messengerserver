package com.pegasus.messengerserver.repository;

import com.pegasus.messengerserver.domain.Message;
import com.pegasus.messengerserver.projection.MessageProjection;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends Repository<Message, Long> {

  @Transactional(readOnly = true)
  List<MessageProjection> findAll();

  @Transactional(readOnly = true)
  MessageProjection findById(Long id);

  @Transactional
  MessageProjection save(Message message);

}
