package br.com.fiap.gerenciador_mottu_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GerenciadorMottuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorMottuApiApplication.class, args);
	}

}
