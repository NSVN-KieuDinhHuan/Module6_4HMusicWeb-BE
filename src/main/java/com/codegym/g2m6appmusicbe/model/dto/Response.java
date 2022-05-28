package com.codegym.g2m6appmusicbe.model.dto;

import lombok.Data;

@Data
public class Response {
    private int status;
    private String message;
    private Object data;
}
