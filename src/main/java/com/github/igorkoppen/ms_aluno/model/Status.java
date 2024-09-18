package com.github.igorkoppen.ms_aluno.model;

public enum Status {
    MATRICULADO(0),
    TRANCADO(1),
    FORMADO(2),
    CANCELADO(3);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Status fromValue(int value) {
        for (Status status : Status.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Não existe status com valor: " + value);
    }
}