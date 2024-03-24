package br.com.hyperativa.cardmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.hyperativa.cardmanagement.model.Card;
import br.com.hyperativa.cardmanagement.respository.CardRepository;
import br.com.hyperativa.cardmanagement.security.EncryptData;
import br.com.hyperativa.cardmanagement.service.exceptions.RecursoNaoEncontradoException;

@Service
public class CardService {
	
	@Autowired
	private CardRepository cardRepository;
	
	public List<Card> listar() {
		return cardRepository.findAll();
	}
	
	public Card buscarPeloId(Long id) {
		
		Card card = cardRepository.findOne(id);
		
		if(card == null) {
			
			/* neste caso ira lancar uma RecursoNaoEncontradoException,  que ira chamar o ExceptionHandler 
			 * correspondente da classe RecursosExceptionHandler do pacote handler, quer ira 
			 *retornar um 404 para o cliente com detalhes da possivel causa do erro */
			throw new RecursoNaoEncontradoException("Nenhum cartão foi encontrado");
		}
		card.setNumero(EncryptData.decriptografar(card.getNumero()));
		return card;
	}
	
	public Card salvar(Card card) {
		
		/* este metodo nao faz atualizacao por isso e preciso garantir que o id sera sempre nulo,
		 * caso contrario o card sera atualizado em vez de criado um novo
		 * */
		
		card.setId(null);
		card.setNumero(EncryptData.criptografar(card.getNumero()));
		return cardRepository.save(card);
	}
	

	public void verificarExistencia(Card card) {
		buscarPeloId(card.getId());
	}
	
	public void atualizar(Card card) {
		
		/* vericando se o card realmente existe, poderia ter
		 * chamado o metodo buscarPeloId direto, mas criei o metodo
		 *  verificar existencia para ajudar na legibilidade do codigo */
		verificarExistencia(card);
		cardRepository.save(card);
	}
	
	public void deletar(Long id) {
		
		try {			
			cardRepository.delete(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new RecursoNaoEncontradoException("O Cartão não foi encontrado");
		}
	}

}
