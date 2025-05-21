package br.com.fiap.gerenciador_mottu_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.gerenciador_mottu_api.dto.MotoDTO;
import br.com.fiap.gerenciador_mottu_api.model.Moto;
import br.com.fiap.gerenciador_mottu_api.repository.MotoRepository;
import br.com.fiap.gerenciador_mottu_api.service.MotoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/motos")
@Slf4j
public class MotoController {

    @Autowired
    private MotoService motoService;

    @Autowired
    private MotoRepository motoRepo;

    @PostMapping("/entrada")
    public ResponseEntity<MotoDTO> entrada(@RequestBody @Valid MotoDTO dto) {
        Moto entrada = motoService.registrarEntrada(dto);
        MotoDTO motoDTO = MotoDTO.builder()
                .id(entrada.getId())
                .modelo(entrada.getModelo())
                .iotIdentificador(entrada.getIotIdentificador())
                .dataEntrada(entrada.getDataEntrada())
                .dataSaida(entrada.getDataSaida())
                .setorId(entrada.getSetor().getId())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(motoDTO);
    }
    //ajustar para path
        @PatchMapping("/saida/{id}")
        public ResponseEntity<MotoDTO> saida(@PathVariable Long id) {
        Moto saida = motoService.registrarSaida(id);
        MotoDTO motoDTO = MotoDTO.builder()
                .id(saida.getId())
                .modelo(saida.getModelo())
                .iotIdentificador(saida.getIotIdentificador())
                .dataEntrada(saida.getDataEntrada())
                .dataSaida(saida.getDataSaida())
                .setorId(saida.getSetor().getId())
                .build();
        return ResponseEntity.ok(motoDTO);
        }

    @GetMapping("/ativas")
    public Page<MotoDTO> ativas(
            @PageableDefault(size = 10, sort = "dataEntrada", direction = Direction.DESC) Pageable pageable) {
        return motoRepo.findByDataSaidaIsNull(pageable)
                .map(m -> MotoDTO.builder()
                        .id(m.getId())
                        .modelo(m.getModelo())
                        .iotIdentificador(m.getIotIdentificador())
                        .dataEntrada(m.getDataEntrada())
                        .dataSaida(m.getDataSaida())
                        .setorId(m.getSetor().getId())
                        .build());
    }

    @GetMapping
    public Page<MotoDTO> todas(
            @PageableDefault(size = 10, sort = "dataEntrada", direction = Direction.DESC) Pageable pageable) {
        return motoRepo.findAll(pageable)
                .map(m -> MotoDTO.builder()
                        .id(m.getId())
                        .modelo(m.getModelo())
                        .iotIdentificador(m.getIotIdentificador())
                        .dataEntrada(m.getDataEntrada())
                        .dataSaida(m.getDataSaida())
                        .setorId(m.getSetor().getId())
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MotoDTO dto) {
        Moto motoAtualizada = motoService.atualizar(id, dto);
        MotoDTO motoDTO = MotoDTO.builder()
                .id(motoAtualizada.getId())
                .modelo(motoAtualizada.getModelo())
                .iotIdentificador(motoAtualizada.getIotIdentificador())
                .dataEntrada(motoAtualizada.getDataEntrada())
                .dataSaida(motoAtualizada.getDataSaida())
                .setorId(motoAtualizada.getSetor().getId())
                .build();
        return ResponseEntity.ok(motoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoDTO> buscarPorId(@PathVariable Long id) {
        return motoRepo.findById(id)
                .map(m -> MotoDTO.builder()
                        .id(m.getId())
                        .modelo(m.getModelo())
                        .iotIdentificador(m.getIotIdentificador())
                        .dataEntrada(m.getDataEntrada())
                        .dataSaida(m.getDataSaida())
                        .setorId(m.getSetor() != null ? m.getSetor().getId() : null)
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}