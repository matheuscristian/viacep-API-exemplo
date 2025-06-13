package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.model.FuncionarioPorCargoModel;

@Repository
public interface FuncionarioPorCargoRepository extends JpaRepository<FuncionarioPorCargoModel, Long> {
    List<FuncionarioPorCargoModel> findByCargoId(Long cargoId);
    List<FuncionarioPorCargoModel> findByFuncionarioId(Long funcionarioId);
    List<FuncionarioPorCargoModel> findByCargoIdAndFuncionarioId(Long cargoId, Long funcionarioId);
}
