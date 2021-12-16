package com.demo.qrcode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Setter
@ToString
public class QrCode {

  private int size;
  private String bgColor;
  private String bgImage;
  private String content;
  private String dir;
  private String ext;
  private String logo;
  private int width;
  private int height;

  @Builder
  public QrCode(int size, String bgColor, String bgImage, String content, String dir,
                String ext, String logo) {
    this.size = size;
    this.bgColor = bgColor;
    this.bgImage = bgImage;
    this.content = content;
    this.dir = dir;
    this.ext = ext;
    this.logo = logo;
    this.width = size;
    this.height = size;
  }

  public int randomColor() {
    int r, g, b;
    Random generator = new Random();
    r = generator.nextInt(256);
    g = generator.nextInt(256);
    b = generator.nextInt(256);
    return new Color(r, g, b).getRGB();
  }

  public String generate() {
    log.debug("generate");
    log.debug(this.toString());
    // Create new configuration that specifies the error correction
    Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

    QRCodeWriter writer = new QRCodeWriter();
    BitMatrix bitMatrix;
    ByteArrayOutputStream os = new ByteArrayOutputStream();

    String imagePath = null;
    try {
      // init directory
      cleanDirectory(dir);
      initDirectory(dir);
      // Create a qr code with the url as content and a size of WxH px
      bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

      // Load QR image
      BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix,
              getMatrixConfig(randomColor(), randomColor()));

      // Load logo image
      BufferedImage overly = getOverly(logo);

      // Calculate the delta height and width between QR code and logo
      float deltaHeight = qrImage.getHeight() - overly.getHeight();
      float deltaWidth = qrImage.getWidth() - overly.getWidth();

      // Initialize combined image
      BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = (Graphics2D) combined.getGraphics();

      // Write QR code to new image at position 0/0
      g.drawImage(qrImage, 0, 0, null);
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

      // Write logo into combine image at position (deltaWidth / 2) and (deltaHeight / 2).
      // Background: Left/Right and Top/Bottom must be the same space for the logo to be centered
      g.drawImage(overly, Math.round(deltaWidth / 2), Math.round(deltaHeight / 2), null);

      // Write combined image as PNG to OutputStream
      ImageIO.write(combined, "png", os);
      // Store Image
      imagePath = dir + "/" + generateRandomTitle(new Random(), 9) + ext;
      Files.copy(new ByteArrayInputStream(os.toByteArray()),
              Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
    } catch (WriterException e) {
      e.printStackTrace();
      log.error("WriterException ", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("IOException ", e);
    }
    return imagePath;
  }

  public String generateOnly() {
    log.debug("generateOnly");
    // Create new configuration that specifies the error correction
    Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

    QRCodeWriter writer = new QRCodeWriter();
    BitMatrix bitMatrix;
    String imagePath = null;

    try {
      // init directory
      cleanDirectory(dir);
      initDirectory(dir);
      // Create a qr code with the url as content and a size of WxH px
      bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

      // Load QR image
      BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, getMatrixConfig(randomColor(), randomColor()));

      Graphics g = qrImage.getGraphics();
      g.setColor(Color.BLACK);
      log.debug("{}", size/10);

//      Font font = g.getFont();
//      FontMetrics metrics = g.getFontMetrics(font);
//      int strWidth = metrics.stringWidth(content);
      g.setFont(new Font("Arial", Font.PLAIN, size/20));
//      log.debug("{} {}", strWidth, size/2 - strWidth/2);
      log.debug(content);
//      g.drawString(content, size/2 - strWidth/2, size/20); // 문자열 삽입
      g.drawString(content, size/20, size/20); // 문자열 삽입

      // Store Image
      imagePath = dir + "/" + generateRandomTitle(new Random(), 9) + ext;
      ImageIO.write(qrImage, "png", new File(imagePath));

//      Files.copy(new ByteArrayInputStream(os.toByteArray()),
//              Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
//      ImageIO.write(image, "png", new File("D:/temp/문자열삽입.png")); // 문자열이 삽입된 PNG 파일 저장
      g.dispose();
    } catch (WriterException e) {
      e.printStackTrace();
      log.error("WriterException ", e);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("IOException ", e);
    }
    return imagePath;
  }

  private BufferedImage getOverly(String logo) throws IOException {
    log.debug("getOverly {}", logo);
    return ImageIO.read(new File(logo));
  }

  private BufferedImage getOverlyURL(String logo) throws IOException {
    log.debug("getOverlyURL {}", logo);
    URL url = new URL(logo);
    return ImageIO.read(url);
  }

  private void initDirectory(String dir) throws IOException {
    log.debug("initDirectory {}", dir);
    Files.createDirectories(Paths.get(dir));
  }

  private void cleanDirectory(String dir) {
    log.debug("cleanDirectory {}", dir);
    try {
      Files.walk(Paths.get(dir), FileVisitOption.FOLLOW_LINKS)
              .sorted(Comparator.reverseOrder())
              .map(Path::toFile)
              .forEach(File::delete);
    } catch (IOException e) {
      // Directory does not exist, Do nothing
      e.printStackTrace();
    }
  }

  private MatrixToImageConfig getMatrixConfig(int onColor, int offColor) {
    log.debug("getMatrixConfig");
    // ARGB Colors
    // Check Colors ENUM
    return new MatrixToImageConfig(onColor, offColor);
//    return new MatrixToImageConfig(Color.WHITE.getRGB(), Color.ORANGE.getRGB());
//    return new MatrixToImageConfig(Colors.WHITE.getArgb(), Colors.ORANGE.getArgb());
  }

  private String generateRandomTitle(Random random, int length) {
    log.debug("generateRandomTitle");
    return random.ints(48, 122)
            .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
            .mapToObj(i -> (char) i)
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
            .toString();
  }

  public static void main(String[] args) {
//    new QrCode().generate();
//    new QrCode().generateOnly();
  }
}