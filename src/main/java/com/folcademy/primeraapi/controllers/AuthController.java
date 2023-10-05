package com.folcademy.primeraapi.controllers;
import com.folcademy.primeraapi.exceptions.exceptionkinds.UnauthorizedException;
import com.folcademy.primeraapi.models.Dtos.SignupRequestDTO;
import com.folcademy.primeraapi.security.AuthCredentials;
import com.folcademy.primeraapi.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDTO signUpRequest) {
        return ResponseEntity.ok(authService.register(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthCredentials loginRequest) throws UnauthorizedException {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
