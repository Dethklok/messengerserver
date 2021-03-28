package com.pegasus.messengerserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {

  @GetMapping
  public List<Map<String, String>> getAll() {
    return new ArrayList<Map<String, String>>() {{
      add(new HashMap<String, String>() {{
        put("id", "1");
        put("text", "First text");
      }});
      add(new HashMap<String, String>() {{
        put("id", "2");
        put("text", "Second text");
      }});
    }};
  }

}
