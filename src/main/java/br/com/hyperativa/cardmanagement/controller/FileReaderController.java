package br.com.hyperativa.cardmanagement.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.hyperativa.cardmanagement.model.Card;
import br.com.hyperativa.cardmanagement.service.CardService;

@RestController
public class FileReaderController {
	
	@Autowired
	private CardService cardService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(FileReaderController.class);
	
	@PostMapping("/readFile")
	public String readFile(@RequestParam("file") MultipartFile file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file.getContentType()));
			String line;	
			String lote = "";
			String cardNumber = "";
			while ((line = reader.readLine()) != null) {

				if ((!line.substring(0, 18).equals("DESAFIO-HYPERATIVA"))) {
					
					if (line.length() > 7) {

						lote = line.substring(1, 7);
						cardNumber = line.substring(7, 26);
						Card card = new Card();
						card.setLote(lote);
						card.setNumero(cardNumber);
						
						try {
							cardService.salvar(card);
							LOGGER.info("Cartão Salvo");
						} catch (Exception e) {
							LOGGER.info("Erro ao salvar cartão");
						}
						LOGGER.info("Lote: " + lote + ", Card Number: " + cardNumber);
						
					}
				}
				
			}
			reader.close();
			return "File processed successfully!";
		} catch (IOException e) {
			return "Error: " + e.getMessage();
		}
	}
	
}