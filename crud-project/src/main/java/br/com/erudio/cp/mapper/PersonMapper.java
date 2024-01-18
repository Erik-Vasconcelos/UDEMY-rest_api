package br.com.erudio.cp.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import br.com.erudio.cp.data.vo_v2.PersonVOV2;
import br.com.erudio.cp.model.Person;

@Component
public class PersonMapper {

	public PersonVOV2 convertEntityToVo(Person person) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setPostalCode(person.getPostalCode());
		vo.setBirtDay(LocalDate.now());

		return vo;
	}

	public Person convertVoToEntity(PersonVOV2 vo) {
		Person person = new Person();
		person.setId(vo.getId());
		person.setFirstName(vo.getFirstName());
		person.setLastName(vo.getLastName());
		person.setPostalCode(vo.getPostalCode());

		return person;
	}

}
