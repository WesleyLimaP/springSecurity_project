package com.gestao.projeto.master.repository;
import com.gestao.projeto.master.DTO.UserDto;
import com.gestao.projeto.master.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.tasks t WHERE u.id = :id ")
    public List<User> basicSelect(Long id);
}
