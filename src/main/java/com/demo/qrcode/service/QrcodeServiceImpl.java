package com.demo.qrcode.service;

import com.demo.qrcode.dto.QrcodeDto;
import com.demo.qrcode.util.QrCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class QrcodeServiceImpl implements QrcodeService {

  private String bgColor;
  private String bgImage;
  private String content;

  @Value("${qrcode.dir}")
  private String dir;

  @Value("${qrcode.ext}")
  private String ext;

  @Value("${qrcode.logo}")
  private String logo;

  @Value("${qrcode.width}")
  private int width;

  @Value("${qrcode.height}")
  private int height;

  @Override
  public String home() {
    return "home";
  }

  @Override
  public String homeParam(Map<String, String> param) {
    log.debug(param.toString());
    return param.toString();
  }

  @Override
  public String generate(Map<String, String> param) {
    log.debug(param.toString());
    QrCode qrCode = new QrCode(
            Integer.parseInt(param.get("size")), param.get("bgColor"), param.get("bgImage"), param.get("content"),
            dir, ext, logo
    );
    log.debug(qrCode.toString());
    return qrCode.generateOnly();
  }

  @Override
  public String generateDto(QrcodeDto dto) {
    log.debug(dto.toString());
    QrCode qrCode = new QrCode(
            dto.getSize(), dto.getBgColor(), dto.getBgImage(), dto.getContent(),
            dir, ext, logo
    );
    log.debug(qrCode.toString());
    return qrCode.generateOnly();
  }
}
