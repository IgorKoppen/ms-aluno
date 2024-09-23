package com.github.igorkoppen.ms_aluno.services;

import com.github.igorkoppen.ms_aluno.dto.AlunoDTO;
import com.github.igorkoppen.ms_aluno.exception.ResourceNotFoundException;
import com.github.igorkoppen.ms_aluno.repository.AlunoRepository;
import com.github.igorkoppen.ms_aluno.service.AlunoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class AlunoServiceIT {

    @Autowired
    private AlunoService service;
    @Autowired
    private AlunoRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalPagamento;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 50L;
        countTotalPagamento = 5L;
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists() {
        service.delete(existingId);
        Assertions.assertEquals(countTotalPagamento - 1, repository.count());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void findAllShouldReturnListOfAlunoDTO(){

        var result = service.findAll();
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalPagamento, result.size());
        Assertions.assertEquals("joao.silva@example.com", result.get(0).getEmail());
        Assertions.assertEquals("João Silva", result.get(0).getNome());
        Assertions.assertEquals("Maria Oliveira", result.get(1).getNome());

    }


}
