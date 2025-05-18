package br.com.fiap.gerenciador_mottu_api.service;

import br.com.fiap.gerenciador_mottu_api.model.Patio;
import br.com.fiap.gerenciador_mottu_api.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatioService {

    @Autowired
    private PatioRepository patioRepo;

    public Page<Patio> listarTodos(Pageable pageable) {
        return patioRepo.findAll(pageable);
    }

    public Patio buscarPorId(Long id) {
        return patioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
    }

    public Patio criar(Patio patio) {
        return patioRepo.save(patio);
    }

    public Patio atualizar(Long id, Patio patio) {
        Patio existente = buscarPorId(id);
        existente.setNome(patio.getNome());
        return patioRepo.save(existente);
    }

    public void deletar(Long id) {
        patioRepo.deleteById(id);
    }
}