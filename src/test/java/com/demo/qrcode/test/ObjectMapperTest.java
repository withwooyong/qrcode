package com.demo.qrcode.test;

import com.demo.qrcode.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ObjectMapperTest {

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void mapperTest() throws JsonProcessingException {
    Event event = new Event("event", LocalDateTime.now());

    String jsonEvent = objectMapper.writeValueAsString(event);
    System.out.println("jsonEvent = " + jsonEvent);

    //language=JSON
    String sss = "{\"name\": \"event\", \"createdDate\" : \"2021-07-21T00:00:00\"}";
    Event getEvent = objectMapper.readValue(jsonEvent, Event.class);
    System.out.println("getEvent = " + getEvent);
  }
}