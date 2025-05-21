package br.com.fiap.gerenciador_mottu_api.controller;

import br.com.fiap.gerenciador_mottu_api.dto.SetorDTO;
import br.com.fiap.gerenciador_mottu_api.service.SetorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setores")
@Slf4j
public class SetorController {

    @Autowired
    private SetorService setorService;

    @CacheEvict(value = "setores", allEntries = true)
    @GetMapping
    public Page<SetorDTO> index(
            @PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {
        return setorService.listarTodos(pageable);
    }

    @Cacheable(value = "setorPorId", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<SetorDTO> buscarPorId(@PathVariable Long id) {
        SetorDTO dto = setorService.buscarPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SetorDTO> criar(@RequestBody @Valid SetorDTO dto) {
        SetorDTO response = setorService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> atualizar(@PathVariable Long id, @RequestBody @Valid SetorDTO dto) {
        SetorDTO atualizado = setorService.atualizar(id, dto);
        return atualizado != null ? ResponseEntity.ok(atualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean removido = setorService.deletar(id);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}