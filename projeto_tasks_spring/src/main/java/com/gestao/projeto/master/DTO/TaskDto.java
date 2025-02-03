package com.gestao.projeto.master.DTO;

import com.gestao.projeto.master.entity.Task;
import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.projection.TaskProjection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskDto {
    private Long id;
    @NotBlank(message = "titulo requerido")
    private String title;
   @NotNull(message = "descrição requerida")
    private String description;
    @NotBlank(message = "status requerido")
    private String status;

    public TaskDto(Long id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        try {
            this.status = status;
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("sintaxe de status invalida");
        }

    }

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus().name();

    }
    public TaskDto (TaskProjection taskProjection){
        this.id = taskProjection.getId();
        this.title = taskProjection.getTitle();
        this.description = taskProjection.getDescription();
        this.status = taskProjection.getStatus();
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
