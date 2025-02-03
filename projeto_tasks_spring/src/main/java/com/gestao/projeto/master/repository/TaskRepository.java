package com.gestao.projeto.master.repository;

import com.gestao.projeto.master.entity.Task;
import com.gestao.projeto.master.projection.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;

public interface TaskRepository extends JpaRepository <Task, Long> {
    @NativeQuery("SELECT TB_TASK.ID, TB_TASK.DESCRIPTION, TB_TASK.DESCRIPTION, TB_TASK.TITLE, TB_TASK.STATUS\n" +
            "FROM TB_TASK WHERE TB_TASK.USER_ID = :id")
    public List<TaskProjection> findAllTasks(Long id);

    @Modifying
    @NativeQuery("DELETE FROM TB_TASK WHERE TB_TASK.USER_ID = :idUser AND TB_TASK.ID = :idTask")
    public void deleteTask(Long idUser, Long idTask);
}
