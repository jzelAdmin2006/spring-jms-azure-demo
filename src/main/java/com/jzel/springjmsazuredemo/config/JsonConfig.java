package com.jzel.springjmsazuredemo.config;

import com.google.gson.Gson;
import com.jzel.springjmsazuredemo.model.MessageObj;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
public class JsonConfig {
  @Bean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTypeIdPropertyName("typeId");
    converter.setTypeIdMappings(Map.of("com.jzel.springjmsazuredemo.MessageObj", MessageObj.class));
    return converter;
  }
}
