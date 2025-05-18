package br.com.fiap.gerenciador_mottu_api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.gerenciador_mottu_api.model.Moto;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Optional<Moto> findByIotIdentificadorAndDataSaidaIsNull(String iotIdentificador);
    Page<Moto> findByDataSaidaIsNull(Pageable pageable);
}
