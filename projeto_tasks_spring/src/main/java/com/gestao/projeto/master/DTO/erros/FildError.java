package com.gestao.projeto.master.DTO.erros;

public class FildError {
    private String name;
    private String message;

    public FildError(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public FildError() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
