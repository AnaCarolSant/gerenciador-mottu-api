package br.com.fiap.gerenciador_mottu_api.repository;

import br.com.fiap.gerenciador_mottu_api.model.Patio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatioRepository extends JpaRepository<Patio, Long> {
}