package br.com.erudio.cp.unittest.mockito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.cp.data.vo_v1.PersonVO;
import br.com.erudio.cp.exception.RequiredObjectIsNullException;
import br.com.erudio.cp.model.Person;
import br.com.erudio.cp.repository.PersonRepository;
import br.com.erudio.cp.service.PersonService;
import br.com.erudio.cp.unittest.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	MockPerson input;
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	PersonRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Person person = input.mockEntity(1);
		person.setId(1L);
			
		when(repository.findById(1L)).thenReturn(Optional.of(person));

		PersonVO result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertEquals(result.toString(), "links: [</api/person/v1/1>;rel=\"self\"]");
		
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("00000-000", result.getPostalCode());
	}
	
	@Test
	void testInsert() {
		Person entity = input.mockEntity(1);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		PersonVO vo = input.mockVO(1);
		vo.setId(1L);

		PersonVO result = service.insert(vo);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertEquals(result.toString(), "links: [</api/person/v1/1>;rel=\"self\"]");
		
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("00000-000", result.getPostalCode());
		
	}
	
	@Test
	void testInsertPersonNull() {
		Exception ex = assertThrows(RequiredObjectIsNullException.class, () -> service.insert(null));
		
		String expectedMessage = "A null object is not allowed";
		String actualMessage = ex.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		PersonVO result = service.update(1L, vo);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertEquals(result.toString(), "links: [</api/person/v1/1>;rel=\"self\"]");
		
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("00000-000", result.getPostalCode());
	}
	
	@Test
	void testUpdatePersonNull() {
		Exception ex = assertThrows(RequiredObjectIsNullException.class, () -> service.update(1L, null));
		
		String expectedMessage = "A null object is not allowed";
		String actualMessage = ex.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testFindAll() {
		List<Person> list = input.mockEntityList();
			
		when(repository.findAll()).thenReturn(list);

		var peoples = service.findAll();
		
		assertNotNull(peoples);
		assertEquals(14, peoples.size());
		
		var personOne = peoples.get(1); 
		
		assertNotNull(personOne);
		assertNotNull(personOne.getId());
		assertNotNull(personOne.getLinks());
		assertEquals(personOne.toString(), "links: [</api/person/v1/1>;rel=\"self\"]");
		
		assertEquals("First Name Test1", personOne.getFirstName());
		assertEquals("Last Name Test1", personOne.getLastName());
		assertEquals("00000-000", personOne.getPostalCode());
		
		var personFour = peoples.get(4); 
		
		assertNotNull(personFour);
		assertNotNull(personFour.getId());
		assertNotNull(personFour.getLinks());
		assertEquals(personFour.toString(), "links: [</api/person/v1/4>;rel=\"self\"]");
		
		assertEquals("First Name Test4", personFour.getFirstName());
		assertEquals("Last Name Test4", personFour.getLastName());
		assertEquals("00000-000", personFour.getPostalCode());
		
		var personSeven = peoples.get(7); 
		
		assertNotNull(personSeven);
		assertNotNull(personSeven.getId());
		assertNotNull(personSeven.getLinks());
		assertEquals(personSeven.toString(), "links: [</api/person/v1/7>;rel=\"self\"]");
		
		assertEquals("First Name Test7", personSeven.getFirstName());
		assertEquals("Last Name Test7", personSeven.getLastName());
		assertEquals("00000-000", personSeven.getPostalCode());
	}

	@Test
	void testDelete() {
		Person person = input.mockEntity(1);
		person.setId(1L);
			
		when(repository.findById(1L)).thenReturn(Optional.of(person));

		service.delete(1L);;
	}

}
