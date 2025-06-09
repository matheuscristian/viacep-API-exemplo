package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.model.CargoModel;

@Repository
public interface CargoRepository extends JpaRepository<CargoModel, Long> {
    
}
