package com.github.igorkoppen.ms_aluno.tests;

import com.github.igorkoppen.ms_aluno.dto.AlunoDTO;
import com.github.igorkoppen.ms_aluno.model.Aluno;
import com.github.igorkoppen.ms_aluno.model.Status;

public class Factory {

    public static Aluno createAluno() {
        return new Aluno(1L,"Igor Koppen", "igor.pereira@example.com", "senha202", "93507", Status.MATRICULADO, "SIPG");
    }

    public static AlunoDTO createAlunoDTO() {
        Aluno aluno = createAluno();
        return new AlunoDTO(aluno);
    }

    public static AlunoDTO createAlunoWithoutIdDTO() {
        Aluno aluno = createAluno();
        aluno.setId(null);
        return new AlunoDTO(aluno);
    }
}
