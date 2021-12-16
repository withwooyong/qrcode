package com.demo.qrcode.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CarDto {
  private String make;
  private int seatCount;
  private String type;
}
