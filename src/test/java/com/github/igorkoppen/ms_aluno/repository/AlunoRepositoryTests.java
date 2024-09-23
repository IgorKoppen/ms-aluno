package com.github.igorkoppen.ms_aluno.repository;

import com.github.igorkoppen.ms_aluno.model.Aluno;
import com.github.igorkoppen.ms_aluno.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class AlunoRepositoryTests {

    @Autowired
    private AlunoRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalAlunos;

    @BeforeEach
    void setup() throws Exception{
        existingId = 1L;
        nonExistingId = 100L;

        countTotalAlunos = 5L;
    }

    @Test
    @DisplayName("Deveria excluir aluno quando Id existe")
    public void deleteShouldDeletObjectWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Aluno> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("save Deveria salvar objeto com auto incremento quando Id é nulo")
    public void saveShouldPersistWithAutIncrementWhenIdIsNull(){
        Aluno aluno = Factory.createAluno();
        aluno.setId(null);
        aluno = repository.save(aluno);
        Assertions.assertNotNull(aluno.getId());
        Assertions.assertEquals(countTotalAlunos + 1, aluno.getId());
    }

    @Test
    @DisplayName("findById Deveria retornar um Optional não vazio quando o Id existe")
    public void findByIdShoulReturnNonEmptyOptionalWhenExistsId(){
        Optional<Aluno> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("findById Deveria retornar um Optional vazio quando o Id não existe")
    public void findByIdShoulReturnEmptyOptionalWhenIdDoesNotExists(){
        Optional<Aluno> result = repository.findById(nonExistingId);
        Assertions.assertFalse(result.isPresent());
        Assertions.assertTrue(result.isEmpty());
    }


}
