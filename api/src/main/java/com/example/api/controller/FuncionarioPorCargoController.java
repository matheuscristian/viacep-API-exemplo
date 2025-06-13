package com.example.api.controller;

import com.example.api.model.FuncionarioPorCargoModel;
import com.example.api.repository.FuncionarioPorCargoRepository;
import com.example.api.repository.CargoRepository;
import com.example.api.repository.FuncionarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios-por-cargo")
@CrossOrigin("*")
public class FuncionarioPorCargoController {

    @Autowired
    private FuncionarioPorCargoRepository funcionarioPorCargoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    // POST - Criar vínculo
    @PostMapping
    public ResponseEntity<FuncionarioPorCargoModel> criar(@RequestBody FuncionarioPorCargoModel fpc) {
        // Aqui seria ideal validar se funcionario e cargo existem
        if (!funcionarioRepository.existsById(fpc.getFuncionario().getId()) ||
                !cargoRepository.existsById(fpc.getCargo().getId())) {
            return ResponseEntity.badRequest().build();
        }

        FuncionarioPorCargoModel salvo = funcionarioPorCargoRepository.save(fpc);
        return ResponseEntity.ok(funcionarioPorCargoRepository.findById(salvo.getId()).orElseThrow());
    }

    // GET - Listar todos (com filtros opcionais)
    @GetMapping
    public ResponseEntity<List<FuncionarioPorCargoModel>> listar(
            @RequestParam(value = "cargoId", required = false) Long cargoId,
            @RequestParam(value = "funcionarioId", required = false) Long funcionarioId) {

        if (cargoId != null && funcionarioId != null) {
            return ResponseEntity
                    .ok(funcionarioPorCargoRepository.findByCargoIdAndFuncionarioId(cargoId, funcionarioId));
        } else if (cargoId != null) {
            return ResponseEntity.ok(funcionarioPorCargoRepository.findByCargoId(cargoId));
        } else if (funcionarioId != null) {
            return ResponseEntity.ok(funcionarioPorCargoRepository.findByFuncionarioId(funcionarioId));
        } else {
            return ResponseEntity.ok(funcionarioPorCargoRepository.findAll());
        }
    }

    // GET - Detalhar um
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioPorCargoModel> detalhar(@PathVariable Long id) {
        Optional<FuncionarioPorCargoModel> optional = funcionarioPorCargoRepository.findById(id);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioPorCargoModel> atualizar(@PathVariable Long id,
            @RequestBody FuncionarioPorCargoModel fpcAtualizado) {
        Optional<FuncionarioPorCargoModel> optional = funcionarioPorCargoRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        FuncionarioPorCargoModel existente = optional.get();

        // Atualizar campos
        existente.setFuncionario(fpcAtualizado.getFuncionario());
        existente.setCargo(fpcAtualizado.getCargo());
        existente.setDetalhes(fpcAtualizado.getDetalhes());
        existente.setDataInicio(fpcAtualizado.getDataInicio());
        existente.setDataFim(fpcAtualizado.getDataFim());

        funcionarioPorCargoRepository.save(existente);
        return ResponseEntity.ok(existente);
    }

    // DELETE - Excluir
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!funcionarioPorCargoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        funcionarioPorCargoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
