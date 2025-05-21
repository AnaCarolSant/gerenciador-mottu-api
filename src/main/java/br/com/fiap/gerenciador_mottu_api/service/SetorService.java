package br.com.fiap.gerenciador_mottu_api.service;

import br.com.fiap.gerenciador_mottu_api.dto.MotoDTO;
import br.com.fiap.gerenciador_mottu_api.dto.SetorDTO;
import br.com.fiap.gerenciador_mottu_api.model.Patio;
import br.com.fiap.gerenciador_mottu_api.model.Setor;
import br.com.fiap.gerenciador_mottu_api.repository.PatioRepository;
import br.com.fiap.gerenciador_mottu_api.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SetorService {

    @Autowired
    private SetorRepository setorRepo;

    @Autowired
    private PatioRepository patioRepo;

    public Page<SetorDTO> listarTodos(Pageable pageable) {
        return setorRepo.findAll(pageable)
                .map(s -> SetorDTO.builder()
                        .id(s.getId())
                        .nome(s.getNome())
                        .capacidadeMaxima(s.getCapacidadeMaxima())
                        .patioId(s.getPatio() != null ? s.getPatio().getId() : null)
                        .motos(s.getMotos() != null
                                ? s.getMotos().stream()
                                    .map(m -> MotoDTO.builder()
                                            .id(m.getId())
                                            .modelo(m.getModelo())
                                            .iotIdentificador(m.getIotIdentificador())
                                            .dataEntrada(m.getDataEntrada())
                                            .dataSaida(m.getDataSaida())
                                            .setorId(m.getSetor() != null ? m.getSetor().getId() : null)
                                            .build())
                                    .collect(Collectors.toList())
                                : null
                        )
                        .build());
    }

    public SetorDTO buscarPorId(Long id) {
        return setorRepo.findById(id)
                .map(s -> SetorDTO.builder()
                        .id(s.getId())
                        .nome(s.getNome())
                        .capacidadeMaxima(s.getCapacidadeMaxima())
                        .patioId(s.getPatio() != null ? s.getPatio().getId() : null)
                        .motos(s.getMotos() != null
                                ? s.getMotos().stream()
                                    .map(m -> MotoDTO.builder()
                                            .id(m.getId())
                                            .modelo(m.getModelo())
                                            .iotIdentificador(m.getIotIdentificador())
                                            .dataEntrada(m.getDataEntrada())
                                            .dataSaida(m.getDataSaida())
                                            .setorId(m.getSetor() != null ? m.getSetor().getId() : null)
                                            .build())
                                    .collect(Collectors.toList())
                                : null
                        )
                        .build())
                .orElse(null);
    }

    public SetorDTO criar(SetorDTO dto) {
        Patio patio = patioRepo.findById(dto.getPatioId())
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
        Setor setor = Setor.builder()
                .nome(dto.getNome())
                .capacidadeMaxima(dto.getCapacidadeMaxima())
                .patio(patio)
                .build();
        Setor salvo = setorRepo.save(setor);
        return SetorDTO.builder()
                .id(salvo.getId())
                .nome(salvo.getNome())
                .capacidadeMaxima(salvo.getCapacidadeMaxima())
                .patioId(salvo.getPatio() != null ? salvo.getPatio().getId() : null)
                .build();
    }

    public SetorDTO atualizar(Long id, SetorDTO dto) {
        return setorRepo.findById(id)
                .map(existente -> {
                    existente.setNome(dto.getNome());
                    existente.setCapacidadeMaxima(dto.getCapacidadeMaxima());
                    if (dto.getPatioId() != null) {
                        Patio patio = patioRepo.findById(dto.getPatioId())
                                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
                        existente.setPatio(patio);
                    }
                    Setor atualizado = setorRepo.save(existente);
                    return SetorDTO.builder()
                            .id(atualizado.getId())
                            .nome(atualizado.getNome())
                            .capacidadeMaxima(atualizado.getCapacidadeMaxima())
                            .patioId(atualizado.getPatio() != null ? atualizado.getPatio().getId() : null)
                            .build();
                })
                .orElse(null);
    }

    public boolean deletar(Long id) {
        if (!setorRepo.existsById(id)) {
            return false;
        }
        setorRepo.deleteById(id);
        return true;
    }
}
