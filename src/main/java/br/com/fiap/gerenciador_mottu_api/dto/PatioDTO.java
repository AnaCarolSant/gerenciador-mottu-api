package br.com.fiap.gerenciador_mottu_api.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatioDTO {
    private Long id;

    @NotBlank(message = "O nome do pátio é obrigatório")
    private String nome;

    private List<SetorDTO> setores;
}
