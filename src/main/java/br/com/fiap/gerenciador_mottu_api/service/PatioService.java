package br.com.fiap.gerenciador_mottu_api.service;

import br.com.fiap.gerenciador_mottu_api.dto.PatioDTO;
import br.com.fiap.gerenciador_mottu_api.dto.SetorDTO;
import br.com.fiap.gerenciador_mottu_api.model.Patio;
import br.com.fiap.gerenciador_mottu_api.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PatioService {

    @Autowired
    private PatioRepository patioRepo;

    public Page<PatioDTO> listarTodos(Pageable pageable) {
        return patioRepo.findAll(pageable)
                .map(p -> PatioDTO.builder()
                        .id(p.getId())
                        .nome(p.getNome())
                        .setores(p.getSetores() != null
                                ? p.getSetores().stream()
                                    .map(s -> SetorDTO.builder()
                                            .id(s.getId())
                                            .nome(s.getNome())
                                            .capacidadeMaxima(s.getCapacidadeMaxima())
                                            .patioId(s.getPatio() != null ? s.getPatio().getId() : null)
                                            .build())
                                    .collect(Collectors.toList())
                                : null
                        )
                        .build());
    }

    public PatioDTO buscarPorId(Long id) {
        return patioRepo.findById(id)
                .map(p -> PatioDTO.builder()
                        .id(p.getId())
                        .nome(p.getNome())
                        .setores(p.getSetores() != null
                                ? p.getSetores().stream()
                                    .map(s -> SetorDTO.builder()
                                            .id(s.getId())
                                            .nome(s.getNome())
                                            .capacidadeMaxima(s.getCapacidadeMaxima())
                                            .patioId(s.getPatio() != null ? s.getPatio().getId() : null)
                                            .build())
                                    .collect(Collectors.toList())
                                : null
                        )
                        .build())
                .orElse(null);
    }

    public PatioDTO criar(PatioDTO dto) {
        Patio patio = Patio.builder()
                .nome(dto.getNome())
                .build();
        Patio salvo = patioRepo.save(patio);
        return PatioDTO.builder()
                .id(salvo.getId())
                .nome(salvo.getNome())
                .build();
    }

    public PatioDTO atualizar(Long id, PatioDTO dto) {
        return patioRepo.findById(id)
                .map(existente -> {
                    existente.setNome(dto.getNome());
                    Patio atualizado = patioRepo.save(existente);
                    return PatioDTO.builder()
                            .id(atualizado.getId())
                            .nome(atualizado.getNome())
                            .build();
                })
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (!patioRepo.existsById(id)) {
            return false;
        }
        patioRepo.deleteById(id);
        return true;
    }
}