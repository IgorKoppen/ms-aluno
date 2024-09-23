package com.github.igorkoppen.ms_aluno.services;

import com.github.igorkoppen.ms_aluno.dto.AlunoDTO;
import com.github.igorkoppen.ms_aluno.exception.ResourceNotFoundException;
import com.github.igorkoppen.ms_aluno.model.Aluno;
import com.github.igorkoppen.ms_aluno.repository.AlunoRepository;
import com.github.igorkoppen.ms_aluno.service.AlunoService;
import com.github.igorkoppen.ms_aluno.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(SpringExtension.class)
public class AlunoServiceTests {

    @InjectMocks
    private AlunoService service;


    @Mock
    private AlunoRepository repository;

    private Long existingId;
    private Long nonExistingId;

    //próximos testes
    private Aluno aluno;
    private AlunoDTO alunoDTO;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 10L;

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.doNothing().when(repository).deleteById(existingId);
        aluno = Factory.createAluno();
        alunoDTO = new AlunoDTO(aluno);
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(aluno));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(repository.save(any())).thenReturn(aluno);
        Mockito.when(repository.getReferenceById(existingId)).thenReturn(aluno);
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete Deveria não fazer nada quando Id existe")
    public void deleteShouldDoNothingWhenIdExists() {
        // no service, delete é do tipo void
        Assertions.assertDoesNotThrow(
                () -> {
                    service.delete(existingId);
                }
        );
    }

    @Test
    @DisplayName("delete Deveria lançar exceção ResourceNotFoundException quando Id não existe")
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnPagamentoDTOWhenIdExists(){
        AlunoDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
        Assertions.assertEquals(result.getRm(), alunoDTO.getRm());
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnPagamentoDTO(){

        AlunoDTO result = service.insert(alunoDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), aluno.getId());
    }

    @Test
    public void updateShouldReturnPagamentoDTOWhenIdExists(){
        AlunoDTO result = service.update(aluno.getId(), alunoDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
        Assertions.assertEquals(result.getRm(), aluno.getRm());
    }

    @Test
    public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, alunoDTO);
        });
    }

}
