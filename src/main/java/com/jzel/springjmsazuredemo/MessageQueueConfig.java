package com.jzel.springjmsazuredemo;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
@EnableJms
public class MessageQueueConfig {
  @JmsListener(destination = "queue.1", containerFactory = "jmsListenerContainerFactory")
  public void receiveMessage(Message msg) throws JMSException {

    MessageConverter converter = jsonMessageConverter();
    Object object = converter.fromMessage(msg);

    if (object instanceof MessageObj) {
      System.out.println(((MessageObj) object).value());
    }
  }

  public MessageConverter jsonMessageConverter() {
    final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTypeIdPropertyName("typeId");
    Map<String, Class<?>> idClassMapping = new HashMap<>();
    idClassMapping.put("com.jzel.springjmsazuredemo.MessageObj", MessageObj.class);
    converter.setTypeIdMappings(idClassMapping);
    return converter;
  }
}
