package com.demo.qrcode.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class QueryStringArgumentResolver implements HandlerMethodArgumentResolver {

  @Autowired
  private ObjectMapper mapper;

  @Override
  public boolean supportsParameter(final MethodParameter methodParameter) {
    return methodParameter.getParameterAnnotation(QueryStringArgsResolver.class) != null;
  }

  @Override
  public Object resolveArgument(final MethodParameter methodParameter,
                                final ModelAndViewContainer modelAndViewContainer,
                                final NativeWebRequest nativeWebRequest,
                                final WebDataBinderFactory webDataBinderFactory) throws Exception {

    final HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
    final String json = queryStringToJson(request.getQueryString());
    return mapper.readValue(json, methodParameter.getParameterType());
  }

  private String queryStringToJson(String str) {
    StringBuilder res = new StringBuilder("{\"");

    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '=') {
        res.append("\"" + ":" + "\"");
      } else if (str.charAt(i) == '&') {
        res.append("\"" + "," + "\"");
      } else {
        res.append(str.charAt(i));
      }
    }
    res.append("\"" + "}");
    return res.toString();
  }
}