package com.github.igorkoppen.ms_aluno.dto;

import com.github.igorkoppen.ms_aluno.model.Status;

public class StatusDTO {

    private  Status status;


    public StatusDTO(Status status) {
        this.status = status;
    }

    public StatusDTO(int statusValue) {
        this.status = Status.fromValue(statusValue);
    }


    public Status getStatus() {
        return status;
    }
}
