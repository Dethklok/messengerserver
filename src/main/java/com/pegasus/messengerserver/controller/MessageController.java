package com.pegasus.messengerserver.controller;

import com.pegasus.messengerserver.entity.Message;
import com.pegasus.messengerserver.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
@AllArgsConstructor
public class MessageController {
  private final MessageRepository messageRepository;

  @GetMapping
  public List<Message> getAll() {
    return messageRepository.findAll();
  }

  @GetMapping("/{id}")
  public Message findOne(@PathVariable("id") Message message) {
    return message;
  }

  @PostMapping
  public Message save(@RequestBody Message message) {
    return messageRepository.save(message);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    messageRepository.deleteById(id);
  }

  @MessageMapping("/saveMessage")
  @SendTo("/topic/message")
  public Message saveMessage(Message dto) {
    return messageRepository.save(dto);
  }

  @MessageMapping("/updateMessage")
  @SendTo("/topic/message")
  public Message updateMessage(Message message) {
    Message messageFromDB = messageRepository.findById(message.getId()).get();
    BeanUtils.copyProperties(message, messageFromDB, "id");
    return messageRepository.save(messageFromDB);
  }

}
