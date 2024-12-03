package com.jzel.springjmsazuredemo.config;

import com.jzel.springjmsazuredemo.model.MessageObj;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
@EnableJms
@RequiredArgsConstructor
public class MessageQueueConfig {
  private final MessageConverter converter;

  @JmsListener(destination = "queue.1", containerFactory = "jmsListenerContainerFactory")
  public void receiveMessage(final Message msg) throws JMSException {

    final Object object = converter.fromMessage(msg); // TODO be more specific than java.lang.Object

    if (object instanceof MessageObj) {
      System.out.println(((MessageObj) object).value());
    }
  }
}
