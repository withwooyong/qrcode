package com.demo.qrcode.controller;

import com.demo.qrcode.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

///**
// * @RequestParam 을 이용한 쿼리 스트링으로 LocalDateTime을 넘기는 상황 (역직렬화)
// * @RequestBody로 객체안의 필드의 타입이 LocalDateTime 인 상황 (역직렬화)
// * @ResponseBody로 객체를 리턴할때 객체안의 필드의 타입이 LocalDateTime 인 상황 (직렬화)
// */
@Slf4j
@RestController
public class EventController {

  // 역직렬화
  @GetMapping("/event")
//  public ResponseEntity<String> getParam(@RequestParam Map<String, String> currentDate) {
//  public ResponseEntity<String> getParam(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime currentDate) {
  public ResponseEntity<String> getParam(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime currentDate) {
    log.info("currentDate = {} ", currentDate.toString());
    System.out.println(currentDate);
    return ResponseEntity.ok("SUCCESS");
  }

  // 역직렬화
  @PostMapping("/event")
  public ResponseEntity<String> createEvent(@RequestBody Event event) {
    log.info("event = {} ", event);
    return ResponseEntity.ok("SUCCESS");
  }

  // 직렬화
  @GetMapping("/")
  public Event getEvent() {
    return new Event("event", LocalDateTime.now());
  }

  // 역직렬화
  @GetMapping("/event-dto")
  public ResponseEntity<String> getParamDto(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                   Map<String, String> map) {
    log.info("currentDate = {} ", map.toString());
    System.out.println(map);
    return ResponseEntity.ok("SUCCESS");
  }
}