package com.gestao.projeto.master.controller;

import com.gestao.projeto.master.DTO.TaskDto;
import com.gestao.projeto.master.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {
    @Autowired
    TaskService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable long id, JwtAuthenticationToken token){
        return ResponseEntity.ok(service.getTaskService(id, token));
    }
    @PostMapping()
    public ResponseEntity<TaskDto> addTask(@Valid @RequestBody TaskDto dto, JwtAuthenticationToken token){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(service.addTask(dto, token));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, JwtAuthenticationToken token  ){
        service.deleteTask(id, token);
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskDto> getTask( @PathVariable Long id, @Valid @RequestBody TaskDto dto, JwtAuthenticationToken token){
        return ResponseEntity.ok(service.updateTask(id, dto, token));
    }
}
