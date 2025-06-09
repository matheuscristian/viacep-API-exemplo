package com.example.api.controller;

import com.example.api.model.FuncionarioModel;
import com.example.api.repository.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin("*")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    public List<FuncionarioModel> getAll() {
        return funcionarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioModel> getById(@PathVariable Long id) {
        return funcionarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FuncionarioModel create(@Valid @RequestBody FuncionarioModel funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioModel> update(@PathVariable Long id,
            @Valid @RequestBody FuncionarioModel updated) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionario.setNome(updated.getNome());
                    funcionario.setEmail(updated.getEmail());
                    funcionario.setSenha(updated.getSenha());
                    funcionario.setCep(updated.getCep());
                    funcionario.setEndereco(updated.getEndereco());
                    funcionario.setNumero(updated.getNumero());
                    funcionario.setBairro(updated.getBairro());
                    funcionario.setCidade(updated.getCidade());
                    funcionario.setEstado(updated.getEstado());
                    return ResponseEntity.ok(funcionarioRepository.save(funcionario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionarioRepository.delete(funcionario);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
