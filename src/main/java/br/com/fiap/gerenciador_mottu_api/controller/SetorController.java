package br.com.fiap.gerenciador_mottu_api.controller;

import br.com.fiap.gerenciador_mottu_api.dto.SetorDTO;
import br.com.fiap.gerenciador_mottu_api.dto.MotoDTO;
import br.com.fiap.gerenciador_mottu_api.model.Setor;
import br.com.fiap.gerenciador_mottu_api.repository.SetorRepository;
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
@RequestMapping("/setores")
@Slf4j
public class SetorController {

    @Autowired
    private SetorRepository setorRepo;

    @Autowired
    private PatioRepository patioRepo;

    @Cacheable("setores")
    @GetMapping
    public Page<SetorDTO> index(
            @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {
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

    @Cacheable(value = "setorPorId", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<SetorDTO> buscarPorId(@PathVariable Long id) {
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
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SetorDTO> criar(@RequestBody @Valid SetorDTO dto) {
        var patio = patioRepo.findById(dto.getPatioId())
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));

        Setor setor = Setor.builder()
                .nome(dto.getNome())
                .capacidadeMaxima(dto.getCapacidadeMaxima())
                .patio(patio) 
                .build();
        Setor salvo = setorRepo.save(setor);
        SetorDTO response = SetorDTO.builder()
                .id(salvo.getId())
                .nome(salvo.getNome())
                .capacidadeMaxima(salvo.getCapacidadeMaxima())
                .patioId(salvo.getPatio() != null ? salvo.getPatio().getId() : null)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> atualizar(@PathVariable Long id, @RequestBody @Valid SetorDTO dto) {
        return setorRepo.findById(id)
                .map(existente -> {
                    existente.setNome(dto.getNome());
                    existente.setCapacidadeMaxima(dto.getCapacidadeMaxima());

                    Setor atualizado = setorRepo.save(existente);
                    SetorDTO response = SetorDTO.builder()
                            .id(atualizado.getId())
                            .nome(atualizado.getNome())
                            .capacidadeMaxima(atualizado.getCapacidadeMaxima())
                            .patioId(atualizado.getPatio() != null ? atualizado.getPatio().getId() : null)
                            .build();
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!setorRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        setorRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}