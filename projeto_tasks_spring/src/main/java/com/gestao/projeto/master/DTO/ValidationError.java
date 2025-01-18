package com.gestao.projeto.master.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends ErrorDto{
    private List<FildError> fildErrors = new ArrayList<>();

    public ValidationError() {
    }

    public ValidationError(LocalDate moment, Integer status, String error, String path) {
        super(moment, status, error, path);
    }

    public List<FildError> getFildErrors() {
        return fildErrors;
    }
}
