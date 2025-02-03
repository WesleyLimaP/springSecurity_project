package com.gestao.projeto.master.repository;
import com.gestao.projeto.master.DTO.UserDto;
import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.projection.TaskProjection;
import com.gestao.projeto.master.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.roles r WHERE u.id = :id ")
    public Optional<User> findUserByIdRolesEager(Long id);

    @NativeQuery(value = "SELECT TB_USER.ID AS user_id, TB_USER.EMAIL, TB_USER.NAME, TB_USER.PASSWORD, TB_ROLE.ID AS role_id, TB_ROLE.AUTHORITY " +
            "FROM TB_USER " +
            "INNER JOIN TB_USER_ROLE ON TB_USER_ROLE.USER_ID = TB_USER.ID " +
            "INNER JOIN TB_ROLE ON TB_ROLE.ID = TB_USER_ROLE.ROLE_ID " +
            "WHERE TB_USER.EMAIL = :email")
    Optional<UserProjection> findByEmail(String email);

    @Query("SELECT u from User u JOIN FETCH u.roles ")
    public List<UserDto> findAllUsers();
}
