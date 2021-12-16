package com.demo.qrcode.controller;

import com.demo.qrcode.annotation.QueryStringArgsResolver;
import com.demo.qrcode.dto.QrcodeDto;
import com.demo.qrcode.service.QrcodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * 색상을 랜덤하게 생성되게 해두었음.
 * 추후 심심하면 해볼 것
 * QR코드 주변에 이미지 삽입, 색상파라미터 받기
 * http://localhost:8080/v1/qrcode?size=200&content=010-1234-5678
 */
@Slf4j
@RestController
//@RequestMapping(value = "/{ver}", produces = "application/json; charset=utf-8")
@RequestMapping(value = "/{ver}")
public class QrcodeController {

  @Autowired
  private QrcodeService service;

  //  http://localhost:8080/swagger-ui/index.html
  @GetMapping(value = "/home", produces = "application/json; charset=utf-8")
  @ResponseStatus(HttpStatus.OK)
  public String home() {
    log.debug("home");
    return service.home();
  }

  @GetMapping(value = "/home-param", produces = "application/json; charset=utf-8")
  @ResponseStatus(HttpStatus.OK)
  public String homeParam(HttpServletRequest req,
                          @PathVariable String ver,
                          @RequestParam Map<String, String> param) {
    log.debug(param.toString());
    return service.homeParam(param);
  }

  // http://localhost:8080/actuator/

  //  http://localhost:8080/v1/qrcode?size=200&content=010-1234-5678
  @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
  public ResponseEntity<Resource> getQrcode(HttpServletRequest req,
                                            @PathVariable String ver,
                                            @RequestParam Map<String, String> param) throws IOException {
    log.debug(param.toString());
    String imageName = service.generate(param);
    Path path = new File(imageName).toPath();
    FileSystemResource resource = new FileSystemResource(path);
    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
            .body(resource);
  }

  //  http://localhost:8080/v1/qrcode2?size=200&content=010-1234-5678
  @GetMapping(value = "/qrcode2", produces = MediaType.IMAGE_PNG_VALUE)
  public ResponseEntity<Resource> getQrcodeDto(HttpServletRequest req,
                                               @PathVariable String ver,
                                               @QueryStringArgsResolver QrcodeDto dto) throws IOException {
    log.debug(dto.toString());
    String imageName = service.generateDto(dto);
    Path path = new File(imageName).toPath();
    FileSystemResource resource = new FileSystemResource(path);
    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
            .body(resource);
  }

  //  http://localhost:8080/v1/qrcode?size=200&content=010-1234-5678
//  @GetMapping(value = "/qrcode3", produces = MediaType.IMAGE_PNG_VALUE)
//  @ResponseStatus(HttpStatus.OK)
//  public @ResponseBody
//  byte[] getQrcode2(HttpServletRequest req,
//                    @PathVariable String ver,
//                    @RequestParam Map<String, String> param) throws IOException {
//    log.debug(param.toString());
//    String imageName = service.generate(param);
////    InputStream in = getClass().getResourceAsStream("./images/f5WSPcEkW.png");
//    InputStream in = getClass().getResourceAsStream(imageName); // 경로 설정 때문제 주석처리
//    assert in != null;
//    return IOUtils.toByteArray(in);
//  }
}
