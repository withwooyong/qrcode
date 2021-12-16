package com.demo.qrcode.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QrcodeDto {
  private int size;
  private String bgColor;
  private String bgImage;
  private String content;
  private String dir;
  private String ext;
  private String logo;
  private int width;
  private int height;
}
