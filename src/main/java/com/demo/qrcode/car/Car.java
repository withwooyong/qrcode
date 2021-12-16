package com.demo.qrcode.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Car {

  private String make;
  private int numberOfSeats;

  private CarType type;

  public enum CarType {
    SEDAN, TRUCK
  }
}
