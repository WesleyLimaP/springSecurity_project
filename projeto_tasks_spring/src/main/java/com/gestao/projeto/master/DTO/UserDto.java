package com.gestao.projeto.master.DTO;

import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.projection.UserProjection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.*;
import java.util.stream.Collectors;

public class UserDto {
    private Long id;
    @NotBlank(message = "campo requerido")
    private String name;
    @NotBlank(message = "campo requerido")
    @Email(message = "email invalido")
    private String email;
    @NotBlank
    @Size(min = 5, max = 16, message = "a senha deve ter entre 5 3 16 caracteres")
    private String password;
    private Set<RoleDto> roles = new HashSet<>();
    private List<TaskDto> tasks = new ArrayList<>();

    public UserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        tasks = user.getTasks().stream().map(TaskDto::new).collect(Collectors.toList());
        roles = user.getRoles().stream().map(RoleDto::new).collect(Collectors.toSet());
    }
    public UserDto(UserProjection user) {
        id = user.getUserId();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();

        getRoles().add(new RoleDto(user.getRoleId(), user.getAuthority()));
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }
}
