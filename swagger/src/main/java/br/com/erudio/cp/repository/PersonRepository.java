package br.com.erudio.cp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.erudio.cp.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
