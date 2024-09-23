package com.github.igorkoppen.ms_aluno.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.igorkoppen.ms_aluno.dto.AlunoDTO;
import com.github.igorkoppen.ms_aluno.exception.ResourceNotFoundException;
import com.github.igorkoppen.ms_aluno.service.AlunoService;
import com.github.igorkoppen.ms_aluno.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc; //para chamar o endpoint
    //controller tem dependência do service
    //dependência mockada
    @MockBean
    private AlunoService service;
    private AlunoDTO alunoDTO;
    private Long existingId;
    private Long nonExistingId;
    //Converter para JSON o objeto Java e enviar na requisção
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 15L;
        alunoDTO = Factory.createAlunoDTO();
        List<AlunoDTO> list = List.of(alunoDTO);
        when(service.findAll()).thenReturn(list);
        when(service.findById(existingId)).thenReturn(alunoDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        when(service.insert(any())).thenReturn(alunoDTO);
        when(service.update(eq(existingId), any())).thenReturn(alunoDTO);
        when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
    }

    @Test
    public void findAllShouldReturnListAlunosDTO() throws Exception {
        ResultActions result = mockMvc.perform(get("/alunos")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnAlunoDTOWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/alunos/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.nome").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.password").exists());
        result.andExpect(jsonPath("$.rm").exists());
        result.andExpect(jsonPath("$.status").exists());
        result.andExpect(jsonPath("$.turma").exists());

    }

    @Test
    @DisplayName("findById Deve retornar NotFound quando Id não existe - Erro 404")
    public void findByIdShoulReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/alunos/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnProductDTOCreated() throws Exception {


        AlunoDTO newDTO = Factory.createAlunoDTO();
        String jsonBody = objectMapper.writeValueAsString(newDTO);

        mockMvc.perform(post("/alunos")
                        .content(jsonBody) //requisição
                        .contentType(MediaType.APPLICATION_JSON) //requisição
                        .accept(MediaType.APPLICATION_JSON)) //resposta
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
       .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.nome").exists())
        .andExpect(jsonPath("$.email").exists())
        .andExpect(jsonPath("$.password").exists())
        .andExpect(jsonPath("$.rm").exists())
        .andExpect(jsonPath("$.status").exists())
        .andExpect(jsonPath("$.turma").exists());
    }

    @Test
    public void updateShouldReturnPagamentoDTOWhenIdExists() throws Exception {

        //PUT tem corpo - JSON
        //precisamos passar o corpo da requisição
        //Bean objectMapper para converter JAVA para JSON
        String jsonBody = objectMapper.writeValueAsString(alunoDTO);

        mockMvc.perform(put("/alunos/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.rm").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.turma").exists());
    }

    @Test
    @DisplayName("Update Deve retornar NotFound quando Id não existe- Erro 404")
    public void updateShoulReturnNotFoundWhenIdDoesNotExist() throws Exception {

        //PUT tem corpo - JSON
        //precisamos passar o corpo da requisição
        //Bean objectMapper para converter JAVA para JSON
        String jsonBody = objectMapper.writeValueAsString(alunoDTO);

        mockMvc.perform(put("/alunos/{id}", nonExistingId)
                        .content(jsonBody) //requisição
                        .contentType(MediaType.APPLICATION_JSON) //requisição
                        .accept(MediaType.APPLICATION_JSON)) //resposta
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {

        mockMvc.perform(delete("/alunos/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("delete Deve retornar NotFound - Erro 404")
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        mockMvc.perform(delete("/alunos/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }



}
