package com.gestao.projeto.master.repository;

import com.gestao.projeto.master.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository <Task, Long> {

}
