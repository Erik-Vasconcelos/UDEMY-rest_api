package br.com.erudio.cp;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.cp.data.vo_v1.PersonVO;
import br.com.erudio.cp.data.vo_v2.PersonVOV2;
import br.com.erudio.cp.exception.ObjectNotFoundException;
import br.com.erudio.cp.mapper.DozerMapper;
import br.com.erudio.cp.mapper.PersonMapper;
import br.com.erudio.cp.model.Person;
import br.com.erudio.cp.repository.PersonRepository;

@Service
public class PersonService {

	private Logger logger = Logger.getLogger(PersonVO.class.getName());
	
	@Autowired
	private PersonMapper mapper;

	@Autowired
	private PersonRepository personRepository;

	public PersonVO insert(PersonVO personVo) {
		logger.info("Save one personVo");
		Person entity = personRepository.save(DozerMapper.parseObject(personVo, Person.class));

		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVOV2 insertV2(PersonVOV2 personVo) {
		logger.info("Save one personVo v2");
		Person entity = mapper.convertVoToEntity(personVo);

		return mapper.convertEntityToVo(personRepository.save(entity));
	}

	public PersonVO update(Long id, PersonVO personVo) {
		personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No personVo with the id"));

		logger.info("Update one personVo");
		personVo.setId(id);
		Person entity = personRepository.save(DozerMapper.parseObject(personVo, Person.class));

		return DozerMapper.parseObject(entity, PersonVO.class);
	}

	public PersonVO findById(Long id) {
		logger.info("Find one personVo");
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("No personVo with the id"));

		return DozerMapper.parseObject(person, PersonVO.class);
	}

	public List<PersonVO> findAll() {
		logger.info("Find persons");
		return DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
	}

	public void delete(Long id) {
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("No personVo with the id"));

		logger.info("Delete one person");
		personRepository.delete(person);
	}

}
