package com.example.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
public class FuncionarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 255)
    private String senha;

    @NotBlank
    @Size(max = 20)
    private String cep;

    @NotBlank
    @Size(max = 255)
    private String endereco;

    @NotBlank
    @Size(max = 20)
    private String numero;

    @NotBlank
    @Size(max = 255)
    private String bairro;

    @NotBlank
    @Size(max = 255)
    private String cidade;

    @NotBlank
    @Size(max = 100)
    private String estado;
}
