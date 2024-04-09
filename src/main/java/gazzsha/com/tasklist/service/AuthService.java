package gazzsha.com.tasklist.service;

import gazzsha.com.tasklist.web.dto.auth.JwtRequest;
import gazzsha.com.tasklist.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
