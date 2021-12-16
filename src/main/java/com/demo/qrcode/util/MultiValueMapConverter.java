package com.demo.qrcode.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class MultiValueMapConverter {

  public static MultiValueMap<String, String> convert(ObjectMapper objectMapper, Object dto) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    try {
      Map<String, String> map = objectMapper.convertValue(dto,
              new TypeReference<>() {
              });
      params.setAll(map);
    } catch (Exception e) {
      log.error("requestDto={}", dto, e);
      throw new IllegalStateException("QueryString 변환오류");
    }
    return params;
  }
}