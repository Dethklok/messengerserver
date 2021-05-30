package com.pegasus.messengerserver.controller;

import com.pegasus.messengerserver.dto.MessageDto;
import com.pegasus.messengerserver.dto.SaveMessageDto;
import com.pegasus.messengerserver.dto.UpdateMessageDto;
import com.pegasus.messengerserver.repository.MessageRepository;
import com.pegasus.messengerserver.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("message")
@AllArgsConstructor
public class MessageController {
  private final MessageRepository messageRepository;
  private final MessageService messageService;

  @GetMapping
  public List<MessageDto> findAll() {
    return messageService.findAll();
  }

  @GetMapping("/{id}")
  public MessageDto findOne(@PathVariable("id") Long id) {
    return messageService.findOne(id);
  }

  @PostMapping
  public MessageDto save(@RequestBody SaveMessageDto saveMessageDto) {
    return messageService.save(saveMessageDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    messageService.delete(id);
  }

  @MessageMapping("/saveMessage")
  @SendTo("/topic/message")
  public MessageDto saveMessage(SaveMessageDto saveMessageDto, Principal principal) {
    System.out.println(principal.getName());
    return messageService.save(saveMessageDto);
  }

  @MessageMapping("/updateMessage/{id}")
  @SendTo("/topic/message")
  public MessageDto updateMessage(@DestinationVariable Long id, @Payload UpdateMessageDto updateMessageDto,
                                  Principal principal) {
    return messageService.update(id, updateMessageDto);
  }

}
