package com.example.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "funcionario_por_cargo")
@Getter
@Setter
public class FuncionarioPorCargoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "funcionario_id")
    private FuncionarioModel funcionario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cargo_id")
    private CargoModel cargo;

    @Size(max = 255)
    private String detalhes;

    @Column(name = "data_inicio")
    @NotBlank
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;
}
