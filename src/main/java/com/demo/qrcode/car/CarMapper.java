package com.demo.qrcode.car;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//https://mapstruct.org/
@Mapper
public interface CarMapper {

  CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
  @Mapping(source = "numberOfSeats", target = "seatCount")
  CarDto carToCarDto(Car car);
}