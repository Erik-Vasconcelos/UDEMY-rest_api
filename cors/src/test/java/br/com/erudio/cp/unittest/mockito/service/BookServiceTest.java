package br.com.erudio.cp.unittest.mockito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import br.com.erudio.cp.data.vo_v1.BookVO;
import br.com.erudio.cp.exception.RequiredObjectIsNullException;
import br.com.erudio.cp.model.Book;
import br.com.erudio.cp.repository.BookRepository;
import br.com.erudio.cp.service.BookService;
import br.com.erudio.cp.unittest.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	MockBook input;
	
	@InjectMocks
	private BookService service;
	
	@Mock
	BookRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() throws ParseException {
		Book book = input.mockEntity(1);
		book.setId(1L);
			
		when(repository.findById(1L)).thenReturn(Optional.of(book));

		BookVO result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertEquals(result.toString(), "links: [</api/book/v1/1>;rel=\"self\"]");
		
		assertEquals("Title1", result.getTitle());
		assertEquals("Author1", result.getAuthor());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("1971-01-01"), result.getLaunchDate());
		assertEquals(10.00, result.getPrice());
	}
	
	@Test
	void testInsert() throws ParseException {
		Book entity = input.mockEntity(1);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		BookVO vo = input.mockVO(1);
		vo.setId(1L);

		BookVO result = service.insert(vo);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertEquals(result.toString(), "links: [</api/book/v1/1>;rel=\"self\"]");
		
		assertEquals("Title1", result.getTitle());
		assertEquals("Author1", result.getAuthor());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("1971-01-01"), result.getLaunchDate());
		assertEquals(10.00, result.getPrice());
		
	}
	
	@Test
	void testInsertBookNull() {
		Exception ex = assertThrows(RequiredObjectIsNullException.class, () -> service.insert(null));
		
		String expectedMessage = "A null object is not allowed";
		String actualMessage = ex.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() throws ParseException {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		BookVO result = service.update(1L, vo);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertEquals(result.toString(), "links: [</api/book/v1/1>;rel=\"self\"]");
		
		assertEquals("Title1", result.getTitle());
		assertEquals("Author1", result.getAuthor());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("1971-01-01"), result.getLaunchDate());
		assertEquals(10.00, result.getPrice());
	}
	
	@Test
	void testUpdateBookNull() {
		Exception ex = assertThrows(RequiredObjectIsNullException.class, () -> service.update(1L, null));
		
		String expectedMessage = "A null object is not allowed";
		String actualMessage = ex.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testFindAll() throws ParseException {
		List<Book> list = input.mockEntityList();
			
		when(repository.findAll()).thenReturn(list);

		var peoples = service.findAll();
		
		assertNotNull(peoples);
		assertEquals(14, peoples.size());
		
		var bookOne = peoples.get(1); 
		
		assertNotNull(bookOne);
		assertNotNull(bookOne.getId());
		assertNotNull(bookOne.getLinks());
		assertEquals(bookOne.toString(), "links: [</api/book/v1/1>;rel=\"self\"]");
		
		assertEquals("Title1", bookOne.getTitle());
		assertEquals("Author1", bookOne.getAuthor());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("1971-01-01"), bookOne.getLaunchDate());
		assertEquals(10.00, bookOne.getPrice());
		
		var bookFour = peoples.get(4); 
		assertNotNull(bookFour);
		assertNotNull(bookFour.getId());
		assertNotNull(bookFour.getLinks());
		assertEquals(bookFour.toString(), "links: [</api/book/v1/4>;rel=\"self\"]");
		
		assertEquals("Title4", bookFour.getTitle());
		assertEquals("Author4", bookFour.getAuthor());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("1974-01-01"), bookFour.getLaunchDate());
		assertEquals(40.00, bookFour.getPrice());
		
		var bookSeven = peoples.get(7); 
		
		assertNotNull(bookSeven);
		assertNotNull(bookSeven.getId());
		assertNotNull(bookSeven.getLinks());
		assertEquals(bookSeven.toString(), "links: [</api/book/v1/7>;rel=\"self\"]");
		
		assertEquals("Title7", bookSeven.getTitle());
		assertEquals("Author7", bookSeven.getAuthor());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("1977-01-01"), bookSeven.getLaunchDate());
		assertEquals(70.00, bookSeven.getPrice());
	}

	@Test
	void testDelete() {
		Book book = input.mockEntity(1);
		book.setId(1L);
			
		when(repository.findById(1L)).thenReturn(Optional.of(book));

		service.delete(1L);;
	}

}
