package br.com.hyperativa.cardmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.com.hyperativa.cardmanagement.controller.FileReaderController;

@EnableJpaAuditing
@SpringBootApplication
public class CardManagementApplication {

	private final static Logger LOGGER = LoggerFactory.getLogger(CardManagementApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CardManagementApplication.class, args);
		LOGGER.info("CardManagementApplication Iniciou");
	}
}
