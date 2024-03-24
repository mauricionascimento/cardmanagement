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
import br.com.hyperativa.cardmanagement.model.User;
import br.com.hyperativa.cardmanagement.service.CardService;
import br.com.hyperativa.cardmanagement.service.UserService;

@RestController
@RequestMapping("/user")
public class UserResource {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> listar() {
		return ResponseEntity.ok().body(userService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> buscarPeloId(@PathVariable Long id) {

		// se nao encontrar um user com o ID passado como parametro, retorna um 404 NOT FOUND
		User user = userService.buscarPeloId(id);

		return ResponseEntity.ok().body(user);
	}

	@PostMapping
	public ResponseEntity<Void> salvar(@RequestBody User user) {

		user = userService.salvar(user);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody User user) {

		// garantindo que vai atualizar o user correto
		user.setId(id);
		userService.atualizar(user);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		userService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
