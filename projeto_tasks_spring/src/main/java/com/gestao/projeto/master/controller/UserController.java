package com.gestao.projeto.master.controller;

import com.gestao.projeto.master.DTO.UpdateUserRequestDto;
import com.gestao.projeto.master.DTO.UserDto;
import com.gestao.projeto.master.service.exceptions.RessoussesNotFoundException;
import com.gestao.projeto.master.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@EnableMethodSecurity
public class UserController {
    @Autowired
    private UserService service;



    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id, JwtAuthenticationToken authentication){
       service.removeUser(id, authentication);
       return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id, JwtAuthenticationToken token){
        return ResponseEntity.ok(service.getUser(id, token));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> UpdateUser(@PathVariable long id, @Valid @RequestBody UpdateUserRequestDto dto, JwtAuthenticationToken token){
       return ResponseEntity.ok(service.updateUser(id, dto, token));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(service.findAllUsers());
    }

}
