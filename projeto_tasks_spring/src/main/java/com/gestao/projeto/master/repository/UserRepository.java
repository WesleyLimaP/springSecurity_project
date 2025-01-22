package com.gestao.projeto.master.repository;
import com.gestao.projeto.master.DTO.UserDto;
import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.tasks t WHERE u.id = :id ")
    public List<User> basicSelect(Long id);

    @NativeQuery("select TB_USER.EMAIL, TB_USER.NAME, TB_USER.PASSWORD, TB_ROLE.ID, TB_ROLE.AUTHORITY " +
            "from TB_USER " +
            "inner join TB_ROLE on TB_ROLE.ID = TB_USER_ROLE.ROLE_ID " +
            "inner join TB_USER_ROLE on USER_ID = TB_USER.ID " +
            "where TB_USER.EMAIL = :email")
    public List<UserProjection> findByEmail(String email);
}
