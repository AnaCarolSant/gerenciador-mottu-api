package br.com.fiap.gerenciador_mottu_api.model;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "campo obrigatório")
    private String modelo;

    @NotBlank(message = "campo obrigatório")
    @Column(unique = true)
    private String iotIdentificador;

    @NotNull(message = "campo obrigatório")
    private LocalDateTime dataEntrada;

    private LocalDateTime dataSaida = null;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;
}