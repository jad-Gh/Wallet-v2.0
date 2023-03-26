package com.finance.walletV2.SecurityConfig;

import com.finance.walletV2.CustomResponse.CustomResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "/login")
    public String login(@RequestBody  Credentials credentials){
        log.info("Token requested for user: '{}'", credentials.email());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password()));
        String token = tokenService.generateToken(authentication);
        log.info("Token granted: {}", token);
        return token;
    }
}

record Credentials(String email,String password){

}
