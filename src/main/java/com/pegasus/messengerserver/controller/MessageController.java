package com.pegasus.messengerserver.controller;

import com.pegasus.messengerserver.entity.Message;
import com.pegasus.messengerserver.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("message")
@AllArgsConstructor
public class MessageController {
  private final MessageRepository messageRepository;

  @GetMapping
  public List<Message> getAll(HttpServletResponse response) {
    response.setHeader("Set-Cookie", "mycookie=hello; HttpOnly; SameSite=localhost; Path=/; Max-Age=99999999;");
    return messageRepository.findAll();
  }

  @GetMapping("/{id}")
  public Message findOne(@PathVariable("id") Message message) {
    return message;
  }

//  @PostMapping
//  public Message save(@RequestBody Message message) {
//    return messageRepository.save(message);
//  }

  @PutMapping("/{id}")
  public Message update(
    @PathVariable("id") Message messageFromDB,
    @RequestBody Message message) {
    BeanUtils.copyProperties(message, messageFromDB, "id");
    return messageRepository.save(messageFromDB);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    messageRepository.deleteById(id);
  }

  @MessageMapping("/changeMessage")
  @SendTo("/topic/activity")
  public Message change(Message message) {
    return messageRepository.save(message);
  }

  @MessageMapping("/saveMessage")
  @SendTo("/topic/activity")
  public Message save(Message message) {
    System.out.println(message);
    return messageRepository.save(message);
  }

}
