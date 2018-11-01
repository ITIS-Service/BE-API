package com.itis.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itis.service.dto.ExceptionResponseDto;
import com.itis.service.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(new LoginException());
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(objectMapper.writeValueAsString(exceptionResponseDto));
    }

}
