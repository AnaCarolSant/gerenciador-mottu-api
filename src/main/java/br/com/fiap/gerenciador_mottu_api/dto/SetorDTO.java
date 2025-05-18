package br.com.fiap.gerenciador_mottu_api.dto;

import java.util.List;

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
public class SetorDTO {
    private Long id;

    @NotBlank(message = "O nome do setor é obrigatório")
    private String nome;

    @NotNull(message = "A capacidade máxima é obrigatória")
    private Integer capacidadeMaxima;

    private List<MotoDTO> motos;

    @NotNull(message = "O ID do pátio é obrigatório")
    private Long patioId;
}


