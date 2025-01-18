package com.gestao.projeto.master.DTO;

import java.time.LocalDate;

public class ErrorDto {
    LocalDate moment;
    Integer status;
    String error;
    String path;

    public ErrorDto() {
    }

    public ErrorDto(LocalDate moment, Integer status, String error, String path) {
        this.moment = moment;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public LocalDate getMoment() {
        return moment;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
