package com.demo.qrcode.test;

import com.demo.qrcode.dto.MultiValueMapDto;
import com.demo.qrcode.util.MultiValueMapConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MultiValueMapTest {

  @Autowired
  ObjectMapper objectMapper;

  MultiValueMap<String, String> result = null;

  @Test
  @DisplayName("String/long/boolean/LocalDateTime/enum 모두 변환된다")
  void test1() {
    //given
    String expectedName = "name";
    int expectedAmount = 1000;
    boolean expectedChecked = true;
    LocalDateTime expectedDateTime = LocalDateTime.of(2020, 2, 22, 19, 35, 1);

//    Status expectedStatus = MultiValueMapTestDto1.Status.SUCCESS;
    MultiValueMapDto.Status status = MultiValueMapDto.Status.SUCCESS;
    MultiValueMapDto dto = MultiValueMapDto.builder()
            .name(expectedName)
            .amount(expectedAmount)
            .checked(true)
            .dateTime(expectedDateTime)
            .status(status)
            .build();
    //when
    result = MultiValueMapConverter.convert(objectMapper, dto);
//    new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(dto);
    //then
    assertThat(result.size(), is(equalTo(5)));
    assertEquals(result.size(), 5, "success");
    assertEquals(result.size(), 4, "error");
    assertEquals("name", expectedName);
    assertEquals("amount", String.valueOf(expectedAmount));
//    assertEquals("checked", String.valueOf(expectedChecked));
//    assertEquals("dateTime", toStringDateTime(expectedDateTime));
//    assertEquals("status", expectedStatus.name());
  }
}
