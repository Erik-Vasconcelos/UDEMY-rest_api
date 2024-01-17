package br.com.erudio.cp;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.cp.exception.ObjectNotFoundException;
import br.com.erudio.cp.model.Person;
import br.com.erudio.cp.repository.PersonRepository;

@Service
public class PersonService {

	private Logger logger = Logger.getLogger(Person.class.getName());

	@Autowired
	private PersonRepository personRepository;

	public Person insert(Person person) {
		logger.info("Save one person");
		return personRepository.save(person);
	}

	public Person update(Long id, Person person) {
		personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No person with the id"));

		logger.info("Update one person");
		person.setId(id);
		return personRepository.save(person);
	}

	public Person findById(Long id) {
		logger.info("Find one person");
		return personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No person with the id"));
	}

	public List<Person> findAll() {
		logger.info("Find persons");
		return personRepository.findAll();
	}

	public void delete(Long id) {
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("No person with the id"));

		logger.info("Delete one person");
		personRepository.delete(person);
	}

}
