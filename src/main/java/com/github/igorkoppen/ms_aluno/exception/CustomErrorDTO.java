package com.github.igorkoppen.ms_aluno.exception;

public class CustomErrorDTO {
    private String timestamp;
    private Integer status;
    private String error;
    private String path;

    public CustomErrorDTO(String timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public CustomErrorDTO() {
    }

    public String getTimestamp() {
        return timestamp;
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