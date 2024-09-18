package com.github.igorkoppen.ms_aluno.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(Long id) {
        super("Recurso não encontrado com id: " + id);
    }
}