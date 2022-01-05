package br.com.zup.LeadCollector;

import br.com.zup.LeadCollector.config.security.JWT.JWTComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class LeadCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeadCollectorApplication.class, args);
	}

}
