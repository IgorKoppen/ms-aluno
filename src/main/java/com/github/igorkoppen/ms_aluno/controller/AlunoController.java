package com.github.igorkoppen.ms_aluno.controller;

import com.github.igorkoppen.ms_aluno.dto.AlunoDTO;
import com.github.igorkoppen.ms_aluno.service.AlunoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    AlunoService service;

    @PostMapping
    public ResponseEntity<AlunoDTO> insert(@RequestBody @Valid AlunoDTO dto){
       AlunoDTO alunoDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
       return ResponseEntity.created(uri).body(alunoDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> findById(@PathVariable @NotNull Long id){
        AlunoDTO alunoDTO = service.getById(id);
        return ResponseEntity.ok(alunoDTO);
    }
    @GetMapping
    public ResponseEntity<List<AlunoDTO>> findAll(){
        List<AlunoDTO> dtoList = service.findAll();
        return ResponseEntity.ok(dtoList);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> update(@PathVariable @NotNull Long id,
                                           @RequestBody @Valid AlunoDTO dto){
        AlunoDTO alunoDTO = service.update(id,dto);
        return ResponseEntity.ok(alunoDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
