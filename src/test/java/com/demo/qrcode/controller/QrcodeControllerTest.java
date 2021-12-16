package com.demo.qrcode.controller;

import com.demo.qrcode.dto.QrcodeDto;
import com.demo.qrcode.service.QrcodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QrcodeController.class)
class QrcodeControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  QrcodeService service;

  @Test
  @DisplayName("홈")
  void home() throws Exception {
    mvc.perform(get("/v1/home"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello world")));
  }

  @Test
  @DisplayName("홈 파라미터")
  void homeParam() throws Exception {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("size", String.valueOf(300));
    queryParams.add("content", "contentTest");

    mvc.perform(get("/v1/home-param")
                    .params(queryParams)
//                    .param("size", String.valueOf(100))
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//            .andExpect(content().string(containsString("Hello world")));
  }

  @Test
  void getQrcode() throws Exception {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("size", String.valueOf(300));
    queryParams.add("content", "contentTest");

    mvc.perform(MockMvcRequestBuilders
                    .get("/v1/qrcode")
                    .params(queryParams))
//                    .queryParam("size", String.valueOf(500))
//                    .queryParam("content", "010-1234-5678"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
//            .andExpect(content().contentType(MediaType.IMAGE_PNG_VALUE));
  }

//  @Test
//  void getQrcode2() {
//    String url = "http://localhost:8080/v1/";
//    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
//            .queryParam("size", 300)
//            .queryParam("content", "010-1234-5678");
//
//    HttpHeaders headers = new HttpHeaders();
//    headers.set("Accept", MediaType.IMAGE_PNG_VALUE);
//    HttpEntity<?> httpEntity = new HttpEntity<>(headers);
//
//    RestTemplate restTemplate = new RestTemplate();
//    ResponseEntity<Resource> responseEntity =
//            restTemplate.exchange(uriComponentsBuilder.toString(),
//                    HttpMethod.GET, httpEntity, Resource.class);
//    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//  }

//  @Test
//  void getQrcode3() {
//    String url = "http://localhost:8080/v1/";
//    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
//            .queryParam("size", 300)
//            .queryParam("content", "010-1234-5678");
//
////    https://hyeonyeee.tistory.com/34
//    HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =
//            new HttpComponentsClientHttpRequestFactory();
//    httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(3000);
//    RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
//    HttpHeaders httpHeaders = new HttpHeaders();
//    HttpEntity<Resource> httpEntity = new HttpEntity<>(httpHeaders);
//    ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Resource.class);
//    responseEntity.getBody().;
//
//  }

  @Test
  void getQrcodeDto() {
    QrcodeDto dto = QrcodeDto.builder()
            .size(300)
            .content("010-1234-5678")
            .build();
  }

  @Test
  void objectToQueryString() {
    QrcodeDto dto = QrcodeDto.builder()
            .size(300)
            .content("010-1234-5678")
            .build();
//    var queryString = Object.keys(params).map(key => key + '=' + params[key]).join('&');
  }
}