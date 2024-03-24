package br.com.hyperativa.cardmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import br.com.hyperativa.cardmanagement.model.Card;
import br.com.hyperativa.cardmanagement.model.User;
import br.com.hyperativa.cardmanagement.respository.CardRepository;
import br.com.hyperativa.cardmanagement.respository.UserRepository;
import br.com.hyperativa.cardmanagement.service.exceptions.RecursoNaoEncontradoException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Value("${login}")
	private String login;
	
	@Value("${senha}")
	private String senha;

	public User create(User user) {
		String generatedSecuredPasswordHash = BCrypt.hashpw(user.getSenha(), BCrypt.gensalt(12));
		user.setSenha(generatedSecuredPasswordHash);
		return userRepository.save(user);
	}

	public List<User> listar() {
		return userRepository.findAll();
	}

	public User buscarPeloId(Long id) {

		User user = userRepository.findOne(id);

		if (user == null) {

			/*
			 * neste caso ira lancar uma RecursoNaoEncontradoException, que ira chamar o
			 * ExceptionHandler correspondente da classe RecursosExceptionHandler do pacote
			 * handler, quer ira retornar um 404 para o cliente com detalhes da possivel
			 * causa do erro
			 */
			throw new RecursoNaoEncontradoException("Nenhum usuario foi encontrado");
		}

		return user;
	}

//	  @Query("select u from User u where u.emailAddress = ?1")
//	   User findByEmailAddress(String emailAddress);
	public User getByLogin(String login) {

		User user = new User();
		user.setLogin(this.login);
		user.setSenha(this.senha);

		String generatedSecuredPasswordHash = BCrypt.hashpw(user.getSenha(), BCrypt.gensalt(12));
		user.setSenha(generatedSecuredPasswordHash);

		if (login == null) {

			/*
			 * neste caso ira lancar uma RecursoNaoEncontradoException, que ira chamar o
			 * ExceptionHandler correspondente da classe RecursosExceptionHandler do pacote
			 * handler, quer ira retornar um 404 para o cliente com detalhes da possivel
			 * causa do erro
			 */
			throw new RecursoNaoEncontradoException("Nenhum usuario foi encontrado");
		}

		return user;
	}

	public User salvar(User user) {

		/*
		 * este metodo nao faz atualizacao por isso e preciso garantir que o id sera
		 * sempre nulo, caso contrario o user sera atualizado em vez de criado um novo
		 */

		user.setId(null);
		return userRepository.save(user);
	}

	/**
	 * Metodo para verificar a existencia de um user. Chama o metodo buscarPeloId
	 * que se nao encontrar o user lanca uma excecao que lanca um NOT FOUND
	 * 
	 * @param user
	 */
	public void verificarExistencia(User user) {
		buscarPeloId(user.getId());
	}

	public void atualizar(User user) {

		verificarExistencia(user);
		userRepository.save(user);
	}

	public void deletar(Long id) {

		try {
			userRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RecursoNaoEncontradoException("O Usuario não foi encontrado");
		}
	}

}
