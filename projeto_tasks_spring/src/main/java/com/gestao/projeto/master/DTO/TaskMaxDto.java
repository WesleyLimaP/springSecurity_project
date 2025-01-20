package com.gestao.projeto.master.DTO;

import com.gestao.projeto.master.entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskMaxDto {
    private Long id;
    @NotBlank(message = "titulo requerido")
    private String title;
    @NotBlank(message = "descrição requerida")
    private String description;
    @NotBlank(message = "status requerido")
    private String status;
    @NotNull(message = "user requerido")
    private Long UserDtoId;

    public TaskMaxDto( String title, String description, String status, Long userdtoId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.UserDtoId = userdtoId;
    }

    public TaskMaxDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus().name();

    }


    public Long getId() {
        return id;
    }

    public TaskMaxDto() {
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

    public Long getUserDtoId() {
        return UserDtoId;
    }

    public void setUserDtoId(Long userdtoId) {
        UserDtoId = userdtoId;
    }
}
