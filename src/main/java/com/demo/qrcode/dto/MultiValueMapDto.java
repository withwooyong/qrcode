package com.demo.qrcode.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MultiValueMapDto {

  private String name;
  private long amount;
  private boolean checked;
  public enum Status {
    SUCCESS, FAIL
  }

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime dateTime;

  private Status status;

  @Builder
  public MultiValueMapDto(String name, long amount, boolean checked,
                          LocalDateTime dateTime, Status status) {
    this.name = name;
    this.amount = amount;
    this.checked = checked;
    this.dateTime = dateTime;
    this.status = status;
  }
}
