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
public class SetorDTO {
    private Long id;
    private String nome;
    private Integer capacidadeMaxima;
    private List<MotoDTO> motos;
    private Long patioId;
}
