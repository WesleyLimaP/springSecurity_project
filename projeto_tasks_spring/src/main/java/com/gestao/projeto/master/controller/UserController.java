package com.gestao.projeto.master.controller;

import com.gestao.projeto.master.DTO.UserDto;
import com.gestao.projeto.master.service.exceptions.RessoussesNotFoundException;
import com.gestao.projeto.master.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/auth")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.ok(service.registerUser(userDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id){
        service.removeUser(id);
       return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id){
        return ResponseEntity.ok(service.getUser(id));
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> UpdateUser(@PathVariable long id, @RequestBody UserDto dto){
       return ResponseEntity.ok(service.updateUser(id, dto));
    }

}
