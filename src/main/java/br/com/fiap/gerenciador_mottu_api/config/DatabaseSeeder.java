package br.com.fiap.gerenciador_mottu_api.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.gerenciador_mottu_api.model.Moto;
import br.com.fiap.gerenciador_mottu_api.model.Setor;
import br.com.fiap.gerenciador_mottu_api.model.Patio;
import br.com.fiap.gerenciador_mottu_api.repository.MotoRepository;
import br.com.fiap.gerenciador_mottu_api.repository.SetorRepository;
import br.com.fiap.gerenciador_mottu_api.repository.PatioRepository;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner seedDatabase(SetorRepository setorRepository, MotoRepository motoRepository, PatioRepository patioRepository) {
        return args -> {

            Patio patio1 = patioRepository.save(Patio.builder()
                    .nome("Pátio Principal")
                    .build());

            Patio patio2 = patioRepository.save(Patio.builder()
                    .nome("Pátio Secundário")
                    .build());


            Setor setor1 = setorRepository.save(Setor.builder()
                    .nome("Setor Central")
                    .capacidadeMaxima(20)
                    .patio(patio1)
                    .build());

            Setor setor2 = setorRepository.save(Setor.builder()
                    .nome("Setor Norte")
                    .capacidadeMaxima(15)
                    .patio(patio2)
                    .build());

   
            motoRepository.saveAll(List.of(
                Moto.builder()
                    .modelo("Honda Biz")
                    .iotIdentificador("IOT100")
                    .dataEntrada(LocalDateTime.now().minusDays(2))
                    .setor(setor1)
                    .build(),
                Moto.builder()
                    .modelo("Yamaha Factor")
                    .iotIdentificador("IOT101")
                    .dataEntrada(LocalDateTime.now().minusDays(1))
                    .setor(setor1)
                    .build(),
                Moto.builder()
                    .modelo("Suzuki Burgman")
                    .iotIdentificador("IOT102")
                    .dataEntrada(LocalDateTime.now().minusHours(10))
                    .setor(setor2)
                    .build()
            ));
        };
    }
}