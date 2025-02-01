package com.gestao.projeto.master.DTO;

import com.gestao.projeto.master.entity.Role;

public class RoleDto {

    private Long id;
    private String authority;

    public RoleDto() {
    }

    public RoleDto(Long id) {
        this.id = id;
    }

    public RoleDto(Role role) {
        this.id = role.getId();
        this.authority = role.getAuthority();
    }

    public RoleDto( Long id, String authority) {
        this.authority = authority;
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }


    public Long getId() {
        return id;
    }

}
