package com.github.igorkoppen.ms_aluno.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String msg){
        super(msg);
    }
}