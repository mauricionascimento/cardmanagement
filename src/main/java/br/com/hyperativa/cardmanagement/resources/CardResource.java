package br.com.hyperativa.cardmanagement.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.hyperativa.cardmanagement.model.Card;

import br.com.hyperativa.cardmanagement.service.CardService;

@RestController
@RequestMapping("/card")
public class CardResource {

	@Autowired
	private CardService cardService;

	@GetMapping
	public ResponseEntity<List<Card>> listar() {
		return ResponseEntity.ok().body(cardService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Card> buscarPeloId(@PathVariable Long id) {

		// se nao encontrar um card com o ID passado como parametro, retorna um 404 NOT FOUND
		Card card = cardService.buscarPeloId(id);

		return ResponseEntity.ok().body(card);
	}

	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody Card card) {

		card = cardService.salvar(card);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(card.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Card card) {

		// garantindo que vai atualizar o card correto
		card.setId(id);
		cardService.atualizar(card);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		cardService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
