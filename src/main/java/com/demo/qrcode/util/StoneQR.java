package com.demo.qrcode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StoneQR {

  private final String DIR = "./images";
  private final String ext = ".png";
  private final String LOGO = "./images/github.png";
//  private final String LOGO = "http://stoneis.pe.kr/images/stoneblogimg.png";
  private final String CONTENT = "https://github.com/withwooyong/qrcode";
  private final int WIDTH = 300;
  private final int HEIGHT = 300;

  public void generate() {
    log.debug("generate");
    // Create new configuration that specifies the error correction
    Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

    QRCodeWriter writer = new QRCodeWriter();
    BitMatrix bitMatrix;
    ByteArrayOutputStream os = new ByteArrayOutputStream();

    try {
      // init directory
//      cleanDirectory(DIR);
      initDirectory(DIR);

      // Create a qr code with the url as content and a size of WxH px
      bitMatrix = writer.encode(CONTENT, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
      BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, getMatrixConfig());
      BufferedImage overly = getOverly(LOGO);

      float deltaHeight = qrImage.getHeight() - overly.getHeight();
      float deltaWidth = qrImage.getWidth() - overly.getWidth();

      BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = (Graphics2D) combined.getGraphics();

      g.drawImage(qrImage, 0, 0, null);
//      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
      // QR코드 이미지의 정중앙 위치에 덮음.
      g.drawImage(overly, Math.round(deltaWidth / 2), Math.round(deltaHeight / 2), null);
      // 적당한 위치로 조정 (QR코드 이미지 맨 하단)
      //g.drawImage(overly, 70, 270, null);

      ImageIO.write(combined, "png", os);

      Files.copy(new ByteArrayInputStream(os.toByteArray()),
              Paths.get(DIR + "/stone" + ext), StandardCopyOption.REPLACE_EXISTING);
    } catch (WriterException | IOException e) {
      e.printStackTrace();
    }
  }

  private BufferedImage getOverlyURL(String LOGO) throws IOException {
    log.debug("getOverlyURL");
    URL url = new URL(LOGO);
    return ImageIO.read(url);
  }

  private BufferedImage getOverly(String LOGO) throws IOException {
    log.debug("getOverly {}", LOGO);
    File image = new File(LOGO);
    return ImageIO.read(image);
  }

  private void initDirectory(String DIR) throws IOException {
    log.debug("initDirectory");
    Files.createDirectories(Paths.get(DIR));
  }

  private void cleanDirectory(String DIR) {
    log.debug("cleanDirectory");
    try {
      Files.walk(Paths.get(DIR), FileVisitOption.FOLLOW_LINKS)
              .sorted(Comparator.reverseOrder())
              .map(Path::toFile)
              .forEach(File::delete);
    } catch (IOException e) {
      // Directory does not exist, Do nothing
      e.printStackTrace();
    }
  }

  private MatrixToImageConfig getMatrixConfig() {
    log.debug("getMatrixConfig");
    return new MatrixToImageConfig(Colors.WHITE.getArgb(), Colors.ORANGE.getArgb());
  }

  public static void main(String[] args){
    new StoneQR().generate();
  }
}