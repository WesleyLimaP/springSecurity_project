package com.gestao.projeto.master.projection;

public interface UserProjection {
    Long getUserId();    // Alterado de getId() para getUserId()
    String getEmail();
    String getName();
    String getPassword();
    Long getRoleId();
    String getAuthority();
}

