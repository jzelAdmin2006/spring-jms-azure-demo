package com.jzel.springjmsazuredemo.rest;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import com.google.gson.Gson;
import com.jzel.springjmsazuredemo.model.MessageObj;
import jakarta.annotation.Resource;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
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
    try (JMSContext context = connectionFactory.createContext()) {
      JMSProducer jmsProducer = context.createProducer();
      Destination destination;
      destination = context.createQueue("queue.1");

      jmsProducer.setProperty("typeId", "com.jzel.springjmsazuredemo.MessageObj");
      jmsProducer.setProperty("content_type", "application/json");
      jmsProducer.setProperty("created_at", now().format(ISO_LOCAL_DATE_TIME));
      jmsProducer.setProperty("version", "1.0.0");
      jmsProducer.setProperty("origin", "SpringJMSDemo");

      jmsProducer.send(destination, gson.toJson(new MessageObj("Hello, World!")));
    }

    return ResponseEntity.ok().build();
  }
}
