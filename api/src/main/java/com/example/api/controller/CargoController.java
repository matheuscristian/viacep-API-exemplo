package com.example.api.controller;

import com.example.api.model.CargoModel;
import com.example.api.repository.CargoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@CrossOrigin("*")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping
    public List<CargoModel> getAll() {
        return cargoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoModel> getById(@PathVariable Long id) {
        return cargoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CargoModel create(@Valid @RequestBody CargoModel cargo) {
        return cargoRepository.save(cargo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoModel> update(@PathVariable Long id, @Valid @RequestBody CargoModel updated) {
        return cargoRepository.findById(id)
                .map(cargo -> {
                    cargo.setNome(updated.getNome());
                    cargo.setDescricao(updated.getDescricao());
                    return ResponseEntity.ok(cargoRepository.save(cargo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return cargoRepository.findById(id)
                .map(cargo -> {
                    cargoRepository.delete(cargo);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
