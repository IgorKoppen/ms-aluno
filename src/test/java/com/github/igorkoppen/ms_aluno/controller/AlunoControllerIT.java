package com.github.igorkoppen.ms_aluno.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.igorkoppen.ms_aluno.dto.AlunoDTO;
import com.github.igorkoppen.ms_aluno.model.Aluno;
import com.github.igorkoppen.ms_aluno.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AlunoControllerIT {

    @Autowired
    private MockMvc mockMvc;
    private Long existingId;
    private Long nonExistingId;
    private AlunoDTO alunoDTO;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 50L;
        alunoDTO = Factory.createAlunoDTO();
    }

    @Test
    public void findAllShouldReturnListAllPagamentos() throws Exception {

        mockMvc.perform(get("/alunos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("[0].id").value(1))
                .andExpect(jsonPath("[0].nome").isString())
                .andExpect(jsonPath("[0].nome").value("João Silva"))
                .andExpect(jsonPath("[0].email").isString())
                .andExpect(jsonPath("[0].email").value("joao.silva@example.com"))
                .andExpect(jsonPath("[0].password").isString())
                .andExpect(jsonPath("[0].password").value("senha123"))
                .andExpect(jsonPath("[0].rm").isString())
                .andExpect(jsonPath("[0].rm").value("RM12345"))
                .andExpect(jsonPath("[0].status").value("TRANCADO"))
                .andExpect(jsonPath("[0].turma").isString());
    }

    @Test
    public void findByIdShouldReturnPagamentoDTOWhenIdExists() throws Exception {

        mockMvc.perform(get("/alunos/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("nome").isString())
                .andExpect(jsonPath("nome").value("João Silva"))
                .andExpect(jsonPath("status").value("TRANCADO"));
    }

    @Test
    public void findByIdShoulReturnNotFoundWhenIdDoesNotExist() throws Exception {
        //NotFound - código 404
        mockMvc.perform(get("/alunos/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void insertShouldReturnPagamentoDTO() throws Exception {

        Aluno entity = Factory.createAluno();
        entity.setId(null);
        String jsonBody = objectMapper.writeValueAsString(entity);

        mockMvc.perform(post("/alunos")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.nome").value("Igor Koppen"))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.rm").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.turma").exists());


    }

    @Test
    @DisplayName("Insert deve lançar exception quando dados inválidos e retornar status 422")
    public void insertShoulThrowsExceptionWhenInvalidData() throws Exception {
        Aluno entity = new Aluno();


        String bodyJson = objectMapper.writeValueAsString(entity);
        mockMvc.perform(post("/alunos")
                        .content(bodyJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateShouldUpdateAndReturnPagamentoDTOWhenIdExists() throws Exception {
        // status 200
        String jsonBody = objectMapper.writeValueAsString(alunoDTO);
        mockMvc.perform(put("/alunos/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.rm").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.turma").exists())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updateShoulReturnNotFoundWhenIdDoesNotExist() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(alunoDTO);
        mockMvc.perform(put("/alunos/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        mockMvc.perform(delete("/alunos/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        mockMvc.perform(delete("/alunos/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}
