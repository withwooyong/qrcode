package com.demo.qrcode.controller;

import com.demo.qrcode.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  //  @RequestParam 을 이용한 쿼리 스트링으로 LocalDateTime을 넘기는 상황 (역직렬화)
//  @RequestBody로 객체안의 필드의 타입이 LocalDateTime 인 상황 (역직렬화)
//  @ResponseBody로 객체를 리턴할때 객체안의 필드의 타입이 LocalDateTime 인 상황 (직렬화)
  @Test
  @Description(value = "GET TEST")
  void getParam() throws Exception {
    LocalDateTime now = LocalDateTime.now();
    System.out.println(now);
    mvc.perform(get("/event")
                    .param("currentDate", String.valueOf(now))
            )
            .andExpect(status().isOk())
            .andDo(print());
  }

  @Test
  void createEvent() throws Exception {
    LocalDateTime now = LocalDateTime.now();
    System.out.println(now);
    Event event = Event.builder().name("name").eventDate(now).build();
//    String content = objectMapper.writeValueAsString(new Event("", ""));
    String content = objectMapper.writeValueAsString(event);
    mvc.perform(post("/event")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
//            .andExpect(content().string("expectedContent"))
            .andDo(print());
  }

  @Test
  void getEvent() throws Exception {
    mvc.perform(get("/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
  }

  @Test
  void getParamDto() throws Exception {
    LocalDateTime now = LocalDateTime.now();
//    Event event = Event.builder().name("name").eventDate(now).build();
    System.out.println(now);
    mvc.perform(get("/event-dto")
                    .param("name", "test")
                    .param("currentDate", String.valueOf(now))
            )
            .andExpect(status().isOk())
            .andDo(print());
  }
}