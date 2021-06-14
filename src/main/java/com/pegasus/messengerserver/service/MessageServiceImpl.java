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

    /*
      The PreUpdate and PostUpdate callbacks occur before and after the database update operations to entity data
      respectively. These database operations may occur at the time the entity state is updated or they may occur
      at the time state is flushed to the database (which may be at the end of transaction).
      And because of this we need to use saveAndFlush method, otherwise message.updatedAt won't be updated instantly
      and method will return deprecated datetime.
      @See section 3.5.3 of the http://download.oracle.com/otndocs/jcp/persistence-2_1-fr-eval-spec/index.html
     */
    MessageProjection savedMessage = messageRepository.saveAndFlush(message);

    return new MessageDto(savedMessage);
  }

  @Override
  public void delete(Long id) {

  }

}
