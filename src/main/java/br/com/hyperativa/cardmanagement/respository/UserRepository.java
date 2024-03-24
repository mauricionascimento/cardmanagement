package br.com.hyperativa.cardmanagement.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hyperativa.cardmanagement.model.User;


public interface UserRepository extends JpaRepository<User, Long>{

}
