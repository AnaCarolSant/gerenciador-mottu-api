package br.com.fiap.gerenciador_mottu_api.controller;


import br.com.fiap.gerenciador_mottu_api.dto.PatioDTO;
import br.com.fiap.gerenciador_mottu_api.service.PatioService;
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


@RestController
@RequestMapping("/patios")
@Slf4j
public class PatioController {

    @Autowired
    private PatioService patioService;

    @Cacheable("patios")
    @GetMapping
    public Page<PatioDTO> index(
            @PageableDefault(size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {
        return patioService.listarTodos(pageable);
    }

    @Cacheable(value = "patioPorId", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<PatioDTO> buscarPorId(@PathVariable Long id) {
        PatioDTO dto = patioService.buscarPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PatioDTO> criar(@RequestBody @Valid PatioDTO dto) {
        PatioDTO response = patioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PatioDTO dto) {
        PatioDTO atualizado = patioService.atualizar(id, dto);
        return atualizado != null ? ResponseEntity.ok(atualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean removido = patioService.deletar(id);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}