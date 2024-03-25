package com.exampl.tasklist.service;

import com.exampl.tasklist.web.dto.auth.JwtRequest;
import com.exampl.tasklist.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;


public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);

}
