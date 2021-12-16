package com.demo.qrcode.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Event {

  private String name;
  private LocalDateTime eventDate;
}