package com.gestao.projeto.master.controller;

import com.gestao.projeto.master.DTO.LoginRequestDto;
import com.gestao.projeto.master.DTO.ResponseLoginDto;
import com.gestao.projeto.master.DTO.UserDto;
import com.gestao.projeto.master.config.security.SecurityConfig;
import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.repository.UserRepository;
import com.gestao.projeto.master.service.JwtService;
import com.gestao.projeto.master.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@EnableMethodSecurity
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private JwtService jwtService;


    @PreAuthorize( "hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(service.registerUser(userDto));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseLoginDto> login (@Valid @RequestBody LoginRequestDto user) throws IOException {
        User userDetails = (User) service.loadUserByUsername(user.email());
        service.checkLogin(user, userDetails);

        return ResponseEntity.ok(new ResponseLoginDto(jwtService.generateToken(userDetails)));


    }
}
