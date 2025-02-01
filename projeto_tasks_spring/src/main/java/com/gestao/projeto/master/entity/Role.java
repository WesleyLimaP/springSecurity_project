package com.gestao.projeto.master.entity;

import com.gestao.projeto.master.enums.Authority;
import com.gestao.projeto.master.service.exceptions.RessoussesNotFoundException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(Long id, String authority) {
        this.id = id;
       setAuthority(authority);
    }
    public Role(Long id) {
        this.id = id;
    }

    public Role(String authority) {
        setAuthority(authority);
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public void setAuthority(String authority) {
        try {
            this.authority = Enum.valueOf(Authority.class, authority);
        }catch (IllegalArgumentException e){
            throw new RessoussesNotFoundException("role nao encontrado");
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }
}
