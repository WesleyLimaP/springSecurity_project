package com.gestao.projeto.master.projection;

public interface UserProjection {
    public Long getId();
    public String getEmail();
    public String getUserName();
    public String getPassword();
    public Long getRoleId();
    public String getAuthority();
}
