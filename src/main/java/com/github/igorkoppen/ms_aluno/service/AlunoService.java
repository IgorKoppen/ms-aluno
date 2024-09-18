package com.github.igorkoppen.ms_aluno.service;

import com.github.igorkoppen.ms_aluno.dto.AlunoDTO;
import com.github.igorkoppen.ms_aluno.exception.DatabaseException;
import com.github.igorkoppen.ms_aluno.exception.ResourceNotFoundException;
import com.github.igorkoppen.ms_aluno.model.Aluno;
import com.github.igorkoppen.ms_aluno.model.Status;
import com.github.igorkoppen.ms_aluno.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository repository;

    @Transactional
    public AlunoDTO insert(AlunoDTO dto){
        Aluno entity = new Aluno();
        copyToEntity(dto, entity);
        entity.setStatus(Status.MATRICULADO);
        entity = repository.save(entity);
        return new AlunoDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<AlunoDTO> findAll(){
        List<Aluno> alunoList = repository.findAll();
        return alunoList.stream().map(AlunoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public AlunoDTO getById(Long id){
        Aluno aluno = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new AlunoDTO(aluno);
    }
    @Transactional
    public AlunoDTO update(Long id, AlunoDTO dto){
        try{
            Aluno entity = repository.getReferenceById(id);
            copyToEntity(dto, entity);

            entity = repository.save(entity);
            return new AlunoDTO(entity);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }
        try{
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
    private void copyToEntity(AlunoDTO dto, Aluno entity){
    entity.setNome(dto.getNome());
    entity.setPassword(dto.getPassword());
    entity.setEmail(dto.getEmail());
    entity.setRm(dto.getRm());
    if(dto.getStatus() != null) {
        entity.setStatus(dto.getStatus().getStatus());
    }
    entity.setTurma(dto.getTurma());
    }
}
