package br.com.fiap.gerenciador_mottu_api.service;

import org.springframework.stereotype.Service;

import br.com.fiap.gerenciador_mottu_api.dto.MotoDTO;
import br.com.fiap.gerenciador_mottu_api.model.Moto;
import br.com.fiap.gerenciador_mottu_api.model.Setor;
import br.com.fiap.gerenciador_mottu_api.repository.MotoRepository;
import br.com.fiap.gerenciador_mottu_api.repository.SetorRepository;
import java.time.LocalDateTime;

@Service
public class MotoService {

    private final MotoRepository motoRepo;
    private final SetorRepository setorRepo;

    public MotoService(MotoRepository motoRepo, SetorRepository setorRepo) {
        this.motoRepo = motoRepo;
        this.setorRepo = setorRepo;
    }

    public Moto registrarEntrada(MotoDTO dto) {


        Setor setor = setorRepo.findById(dto.getSetorId())
            .orElseThrow(() -> new RuntimeException("Setor inválido"));

        long ocupadas = motoRepo.findAll().stream()
            .filter(m -> m.getSetor().equals(setor) && m.getDataSaida() == null)
            .count();

        if (ocupadas >= setor.getCapacidadeMaxima()) {
            throw new RuntimeException("Setor cheio");
        }

        Moto moto = Moto.builder()
            .modelo(dto.getModelo())
            .iotIdentificador(dto.getIotIdentificador())
            .setor(setor)
            .dataEntrada(LocalDateTime.now())
            .build();

        return motoRepo.save(moto);
    }

    public Moto registrarSaida(Long id) {
        Moto moto = motoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        if (moto.getDataSaida() != null) {
            throw new RuntimeException("Moto já saiu do pátio");
        }

        moto.setDataSaida(LocalDateTime.now());
        return motoRepo.save(moto);
    }


    public Moto atualizar(Long id, MotoDTO dto) {
        Moto moto = motoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        moto.setModelo(dto.getModelo());
        moto.setIotIdentificador(dto.getIotIdentificador());
        Setor setor = setorRepo.findById(dto.getSetorId())
            .orElseThrow(() -> new RuntimeException("Setor inválido"));
        moto.setSetor(setor);
        return motoRepo.save(moto);
    }


    public void deletar(Long id) {
        motoRepo.deleteById(id);
    }
}