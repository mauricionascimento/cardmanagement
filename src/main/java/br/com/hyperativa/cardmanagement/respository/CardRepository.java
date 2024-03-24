package br.com.hyperativa.cardmanagement.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hyperativa.cardmanagement.model.Card;


public interface CardRepository extends JpaRepository<Card, Long>{

}
