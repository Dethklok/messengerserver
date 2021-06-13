package com.pegasus.messengerserver.service;

import com.pegasus.messengerserver.domain.Message;
import com.pegasus.messengerserver.dto.MessageDto;
import com.pegasus.messengerserver.dto.SaveMessageDto;
import com.pegasus.messengerserver.dto.UpdateMessageDto;
import com.pegasus.messengerserver.projection.MessageProjection;
import com.pegasus.messengerserver.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;

  @Override
  public List<MessageDto> findAll() {
    return messageRepository.findAll().stream()
      .map(MessageDto::new)
      .collect(Collectors.toList());
  }

  @Override
  public MessageDto findOne(Long id) {
    MessageProjection projection = messageRepository.findById(id);
    return new MessageDto(projection);
  }

  @Override
  public MessageDto save(SaveMessageDto saveMessageDto) {
    Message message = new Message();

    message.setContent(saveMessageDto.getContent());

    MessageProjection savedMessage = messageRepository.save(message);

    return new MessageDto(savedMessage);
  }

  @Transactional
  @Override
  public MessageDto update(Long id, UpdateMessageDto updateMessageDto) {
    Message message = messageRepository.getOne(id);

    message.setContent(updateMessageDto.getContent());

    MessageProjection savedMessage = messageRepository.saveAndFlush(message);

    return new MessageDto(savedMessage);
  }

  @Override
  public void delete(Long id) {

  }

}
