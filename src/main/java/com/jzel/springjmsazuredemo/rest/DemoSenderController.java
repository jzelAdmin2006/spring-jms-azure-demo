package com.jzel.springjmsazuredemo.rest;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import com.google.gson.Gson;
import com.jzel.springjmsazuredemo.model.MessageObj;
import jakarta.annotation.Resource;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoSenderController {
  @Resource private ConnectionFactory connectionFactory;

  private final Gson gson;

  @PostMapping("/send")
  public ResponseEntity<Void> sendMessage() {
    try (final JMSContext context = connectionFactory.createContext()) {
      final JMSProducer jmsProducer = context.createProducer();
      jmsProducer.setProperty("typeId", "com.jzel.springjmsazuredemo.MessageObj");
      jmsProducer.setProperty("content_type", "application/json");
      jmsProducer.setProperty("created_at", now().format(ISO_LOCAL_DATE_TIME));
      jmsProducer.setProperty("version", "1.0.0");
      jmsProducer.setProperty("origin", "SpringJMSDemo");
      jmsProducer.send(
          context.createQueue("queue.1"), gson.toJson(new MessageObj("Hello, World!")));
    }
    return ResponseEntity.ok().build();
  }
}
