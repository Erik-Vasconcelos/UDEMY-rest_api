package br.com.erudio.cp.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.erudio.cp.controller.PersonController;
import br.com.erudio.cp.data.vo_v1.PersonVO;
import br.com.erudio.cp.exception.ObjectNotFoundException;
import br.com.erudio.cp.exception.RequiredObjectIsNullException;
import br.com.erudio.cp.mapper.DozerMapper;
import br.com.erudio.cp.model.Person;
import br.com.erudio.cp.repository.PersonRepository;

@Service
public class PersonService {

	private Logger logger = Logger.getLogger(PersonVO.class.getName());

	@Autowired
	private PersonRepository personRepository;

	public PersonVO insert(PersonVO personVo) {
		if(personVo == null) throw new RequiredObjectIsNullException();
		
		logger.info("Save one personVo");
		Person entity = personRepository.save(DozerMapper.parseObject(personVo, Person.class));

		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).getPerson(vo.getId())).withSelfRel());
		
		return vo;
	}

	public PersonVO update(Long id, PersonVO personVo) {
		if(personVo == null) throw new RequiredObjectIsNullException();
		
		personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No personVo with the id"));

		logger.info("Update one personVo");
		personVo.setId(id);
		Person entity = personRepository.save(DozerMapper.parseObject(personVo, Person.class));
		
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).getPerson(vo.getId())).withSelfRel());
		
		return vo;
	}

	public PersonVO findById(Long id) {
		logger.info("Find one personVo");
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("No personVo with the id"));

		PersonVO vo = DozerMapper.parseObject(person, PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).getPerson(id)).withSelfRel());
		//methodOn -> método que vair prover o recurso
		// withSelfRel() -> link para si próprio
		
		return vo;
	}

	public List<PersonVO> findAll() {
		logger.info("Find persons");
		
		List<PersonVO> persons = DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
		
		persons.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).getPerson(p.getId())).withSelfRel()));
		
		return persons;
	}

	public void delete(Long id) {
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("No personVo with the id"));

		logger.info("Delete one person");
		personRepository.delete(person);
	}

}
