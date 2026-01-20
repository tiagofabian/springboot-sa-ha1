package com.springboot_sa_ha1;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootSaHa1Application {

	public static void main(String[] args) {


		// Cargar las variables de entorno desde el .env
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		System.setProperty("DB_URL", dotenv.get("DB_URL",""));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME",""));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD",""));

		System.setProperty("JWT_SECRET",dotenv.get("JWT_SECRET",""));
		System.setProperty("JWT_EXPIRATION_MS", "3600000");
		SpringApplication.run(SpringbootSaHa1Application.class, args);
	}

}
