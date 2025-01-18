package com.gestao.projeto.master.DTO;

import com.gestao.projeto.master.entity.Task;
import com.gestao.projeto.master.entity.User;
import jakarta.persistence.*;

public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;

    public TaskDto(Long id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus().name();

    }


    public Long getId() {
        return id;
    }

    public TaskDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
