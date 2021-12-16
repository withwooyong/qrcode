package com.demo.qrcode.car;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

//https://mapstruct.org/
class CarMapperTest {

  @Test
  void carToCarDto() {
    //given
    Car car = new Car("Morris", 5, Car.CarType.SEDAN);

    System.out.println(car);
    //when
    CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);
    System.out.println(carDto.toString());
    //then
    Assertions.assertThat(carDto).isNotNull();
    Assertions.assertThat(carDto).isNotNull();
    Assertions.assertThat(carDto.getMake()).isEqualTo("Morris");
    Assertions.assertThat(carDto.getSeatCount()).isEqualTo(5);
    Assertions.assertThat(carDto.getType()).isEqualTo("SEDAN");
  }
}