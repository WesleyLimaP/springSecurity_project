package com.gestao.projeto.master.repository;

import com.gestao.projeto.master.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @NativeQuery("select * from tb_role where tb_role.authority = :name")
    public Optional<Role> findByAlthority(String name);
}
