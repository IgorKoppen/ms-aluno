package com.github.igorkoppen.ms_aluno.repository;

import com.github.igorkoppen.ms_aluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno,Long> {
}
