package com.folcademy.primeraapi.services;


import com.folcademy.primeraapi.exceptions.exceptionkinds.UnauthorizedException;
import com.folcademy.primeraapi.models.Dtos.SignupRequestDTO;
import com.folcademy.primeraapi.models.Dtos.UserReadDTO;
import com.folcademy.primeraapi.models.entities.UserEntity;
import com.folcademy.primeraapi.models.mappers.UserMapper;
import com.folcademy.primeraapi.security.AuthCredentials;
import com.folcademy.primeraapi.security.TokenUtils;
import com.folcademy.primeraapi.security.UserDetailServiceImpl;
import com.folcademy.primeraapi.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {
    private final UserDetailServiceImpl userDetailService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthService(UserDetailServiceImpl userDetailService, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    public UserReadDTO register(SignupRequestDTO signUpRequest) {
        UserEntity userEntity = userDetailService.registerUser(signUpRequest);
        if (Objects.isNull(userEntity)){
            throw new RuntimeException("Usuario invalido");
        }
        return userMapper.userEntityToUserReadDTO(userEntity);
    }

    public String login(AuthCredentials loginRequest) throws UnauthorizedException{
        try {
            // Intento de autenticación usando el AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Si la autenticación es exitosa, generamos un token.
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = TokenUtils.createToken(userDetails.getName(), userDetails.getUsername());

            // En un caso real, puedes querer devolver el token dentro de un objeto para mayor flexibilidad.
            return "Bearer " + jwt;
        }catch (Exception e){
            throw new UnauthorizedException("Usuario no autorizado");
        }
    }
}
