package com.gestao.projeto.master.DTO;

import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.projection.UserProjection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UpdateUserRequestDto {
    @NotBlank(message = "campo requerido")
    private String name;
    @Email(message = "email invalido")
    private String email;
    @NotBlank
    @Size(min = 5, max = 16, message = "a senha deve ter entre 5 3 16 caracteres")
    private String password;

    public UpdateUserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UpdateUserRequestDto(User user) {
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
    }

    public UpdateUserRequestDto() {
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

}
