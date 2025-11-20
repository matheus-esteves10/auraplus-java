package br.com.fiap.AuraPlus;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Api do Aura Plus", version = "v1"))
@EnableScheduling
public class AuraPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuraPlusApplication.class, args);
	}

}
