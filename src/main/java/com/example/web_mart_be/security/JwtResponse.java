package com.example.web_mart_be.security;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private final String jwtToken;
}
