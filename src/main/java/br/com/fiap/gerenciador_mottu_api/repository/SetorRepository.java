package br.com.fiap.gerenciador_mottu_api.repository;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;


import br.com.fiap.gerenciador_mottu_api.model.Setor;

public interface SetorRepository extends JpaRepository<Setor, Long> {
    Optional<Setor> findByNome(String nome);
}
