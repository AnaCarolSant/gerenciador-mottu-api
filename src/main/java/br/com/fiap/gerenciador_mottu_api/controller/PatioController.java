package br.com.fiap.gerenciador_mottu_api.controller;

import br.com.fiap.gerenciador_mottu_api.dto.MotoDTO;
import br.com.fiap.gerenciador_mottu_api.dto.PatioDTO;
import br.com.fiap.gerenciador_mottu_api.dto.SetorDTO;
import br.com.fiap.gerenciador_mottu_api.model.Patio;
import br.com.fiap.gerenciador_mottu_api.repository.PatioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/patios")
@Slf4j
public class PatioController {

    @Autowired
    private PatioRepository patioRepo;

    @Cacheable("patios")
    @GetMapping
    public Page<PatioDTO> index(
            @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {
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
                                    .collect(Collectors.toList())
                                : null
                        )
                        .build());
    }

    @Cacheable(value = "patioPorId", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<PatioDTO> buscarPorId(@PathVariable Long id) {
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
                                    .collect(Collectors.toList())
                                : null
                        )
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatioDTO> criar(@RequestBody @Valid PatioDTO dto) {
        Patio patio = Patio.builder()
                .nome(dto.getNome())
                .build();
        Patio salvo = patioRepo.save(patio);
        PatioDTO response = PatioDTO.builder()
                .id(salvo.getId())
                .nome(salvo.getNome())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PatioDTO dto) {
        return patioRepo.findById(id)
                .map(existente -> {
                    existente.setNome(dto.getNome());
                    Patio atualizado = patioRepo.save(existente);
                    PatioDTO response = PatioDTO.builder()
                            .id(atualizado.getId())
                            .nome(atualizado.getNome())
                            .build();
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!patioRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        patioRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}