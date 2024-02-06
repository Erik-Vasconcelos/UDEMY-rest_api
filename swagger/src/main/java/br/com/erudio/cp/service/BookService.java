package br.com.erudio.cp.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.cp.controller.BookController;
import br.com.erudio.cp.data.vo_v1.BookVO;
import br.com.erudio.cp.exception.ObjectNotFoundException;
import br.com.erudio.cp.exception.RequiredObjectIsNullException;
import br.com.erudio.cp.mapper.DozerMapper;
import br.com.erudio.cp.model.Book;
import br.com.erudio.cp.repository.BookRepository;

@Service
public class BookService {

	private Logger logger = Logger.getLogger(BookVO.class.getName());

	@Autowired
	private BookRepository bookRepository;

	public BookVO insert(BookVO bookVo) {
		if(bookVo == null) throw new RequiredObjectIsNullException();
		
		logger.info("Save one bookVo");
		Book entity = bookRepository.save(DozerMapper.parseObject(bookVo, Book.class));

		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).getBook(vo.getId())).withSelfRel());
		
		return vo;
	}

	public BookVO update(Long id, BookVO bookVo) {
		if(bookVo == null) throw new RequiredObjectIsNullException();
		
		bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No book with the id"));

		logger.info("Update one book");
		bookVo.setId(id);
		Book entity = bookRepository.save(DozerMapper.parseObject(bookVo, Book.class));
		
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).getBook(vo.getId())).withSelfRel());
		
		return vo;
	}

	public BookVO findById(Long id) {
		logger.info("Find one book");
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("No book with the id"));

		BookVO vo = DozerMapper.parseObject(book, BookVO.class);
		
		vo.add(linkTo(methodOn(BookController.class).getBook(id)).withSelfRel());
		
		return vo;
	}

	public List<BookVO> findAll() {
		logger.info("Find books");
		
		List<BookVO> books = DozerMapper.parseListObjects(bookRepository.findAll(), BookVO.class);
		
		books.stream()
			.forEach(p -> p.add(linkTo(methodOn(BookController.class).getBook(p.getId())).withSelfRel()));
		
		return books;
	}

	public void delete(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("No book with the id"));

		logger.info("Delete one book");
		bookRepository.delete(book);
	}

}
