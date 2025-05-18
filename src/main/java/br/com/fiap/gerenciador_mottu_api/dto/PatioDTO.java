package br.com.fiap.gerenciador_mottu_api.dto;

import java.util.List;

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
    private String nome;
    private List<SetorDTO> setores;
}
