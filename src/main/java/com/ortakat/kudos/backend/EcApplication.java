package com.ortakat.kudos.backend;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}

