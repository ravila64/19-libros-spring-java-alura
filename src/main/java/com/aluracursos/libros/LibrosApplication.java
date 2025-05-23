package com.aluracursos.libros;

import com.aluracursos.libros.main.MainBooks;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MainBooks mainBooks = new MainBooks();
		mainBooks.viewMewu();
	}
}
