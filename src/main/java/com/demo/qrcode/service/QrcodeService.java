package com.demo.qrcode.service;

import com.demo.qrcode.dto.QrcodeDto;

import java.util.Map;

public interface QrcodeService {

  String home();
  String homeParam(Map<String, String> param);
  String generate(Map<String, String> param);
  String generateDto(QrcodeDto dto);
}
