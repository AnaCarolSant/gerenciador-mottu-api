package br.com.fiap.gerenciador_mottu_api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @NotBlank(message = "O identificador IoT é obrigatório")
    private String iotIdentificador;

    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    @NotNull(message = "O setorId é obrigatório")
    private Long setorId;
}