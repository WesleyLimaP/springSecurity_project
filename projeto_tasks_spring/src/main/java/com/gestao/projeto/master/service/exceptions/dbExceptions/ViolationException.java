package com.gestao.projeto.master.service.exceptions.dbExceptions;

public class ViolationException extends RuntimeException {
    public ViolationException(String message) {
        super(message);
    }
}
