package br.com.erudio.cp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.cp.data.vo_v1.PersonVO;
import br.com.erudio.cp.service.PersonService;
import br.com.erudio.cp.util.MediaType;

@RestController
@RequestMapping("/person/v1")
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML })
	public ResponseEntity<PersonVO> getPerson(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.findById(id));
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
	public ResponseEntity<List<PersonVO>> getPersons() {
		return ResponseEntity.status(HttpStatus.OK).body(personService.findAll());
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YAML })
	public ResponseEntity<PersonVO> insert(@RequestBody PersonVO person) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.insert(person));
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YAML })
	public ResponseEntity<PersonVO> update(@PathVariable(name = "id") Long id, @RequestBody PersonVO person) {
		return ResponseEntity.status(HttpStatus.OK).body(personService.update(id, person));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") Long id) {
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
