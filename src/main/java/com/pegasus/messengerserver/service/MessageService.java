package com.pegasus.messengerserver.service;

import com.pegasus.messengerserver.dto.MessageDto;
import com.pegasus.messengerserver.dto.SaveMessageDto;
import com.pegasus.messengerserver.dto.UpdateMessageDto;

import java.util.List;

public interface MessageService {

  List<MessageDto> findAll();
  MessageDto findOne(Long id);
  MessageDto save(SaveMessageDto saveMessageDto);
  MessageDto update(Long id, UpdateMessageDto updateMessageDto);
  void delete(Long id);

}
