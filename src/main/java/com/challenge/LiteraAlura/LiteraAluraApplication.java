package com.challenge.LiteraAlura;

import com.challenge.LiteraAlura.principal.Principal;
import com.challenge.LiteraAlura.repository.AutorRepository;
import com.challenge.LiteraAlura.repository.LibroRepository;
import com.challenge.LiteraAlura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraAluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraAluraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sistema Iniciado");

		LibroService libroService = new LibroService(libroRepository, autorRepository);
		Principal principal = new Principal(libroService);
		principal.iniciarPrograma();
	}
}
