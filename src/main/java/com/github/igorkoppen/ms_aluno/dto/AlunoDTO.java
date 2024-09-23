package com.github.igorkoppen.ms_aluno.dto;

import com.github.igorkoppen.ms_aluno.model.Aluno;
import com.github.igorkoppen.ms_aluno.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class AlunoDTO {


    private Long id;
    @NotBlank(message = "Campo requerido!")
    private String nome;
    @NotBlank(message = "Campo requerido!")
    @Email(message = "Email inválido!")
    private String email;
    @NotBlank(message = "Campo requerido!")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres!")
    private String password;
    @NotBlank(message = "Campo requerido!")
    private String rm;
    @Enumerated(value = EnumType.ORDINAL)
    private Status status;
    @NotBlank(message = "Campo requerido!")
    private String turma;

    public AlunoDTO(Long id, String nome, String email, String password, String rm, Status status, String turma) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.rm = rm;
        this.status = status;
        this.turma = turma;
    }
    public AlunoDTO(Aluno entity){
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.rm = entity.getRm();
        if(entity.getStatus() != null) {
            this.status = entity.getStatus();
        }
        this.turma = entity.getTurma();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
}
