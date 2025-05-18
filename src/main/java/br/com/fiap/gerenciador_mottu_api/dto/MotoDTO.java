package br.com.fiap.gerenciador_mottu_api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotoDTO {
    private Long id;
    private String modelo;
    private String iotIdentificador;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private Long setorId;
}