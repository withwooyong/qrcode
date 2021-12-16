package com.demo.qrcode;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@SpringBootTest
class QrcodeControllerTest {

  @Test
  void getText() {
  }

  @Test
  void getImageWithMediaType() throws IOException {

    InputStream in = getClass().getResourceAsStream("static/images/github.png");
    assert in != null;
    byte[] getImageWithMediaType = IOUtils.toByteArray(in);
    assert getImageWithMediaType != null;

  }

  @Test
  void randomColor() {
    int r, g, b;
    Random generator = new Random();
    r = generator.nextInt(256);
    g = generator.nextInt(256);
    b = generator.nextInt(256);
    System.out.println(new Color(r, g, b).getRGB());
    System.out.println(Color.BLACK.getRGB());
    System.out.println(Color.WHITE.getRGB());
  }
}