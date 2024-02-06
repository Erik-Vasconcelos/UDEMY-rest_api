package br.com.erudio.cp.unittest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.erudio.cp.data.vo_v1.BookVO;
import br.com.erudio.cp.model.Book;

public class MockBook {

	public Book mockEntity() {
		return mockEntity(0);
	}

	public BookVO mockVO() {
		return mockVO(0);
	}

	public List<Book> mockEntityList() {
		List<Book> books = new ArrayList<Book>();
		for (int i = 0; i < 14; i++) {
			books.add(mockEntity(i));
		}
		return books;
	}

	public List<BookVO> mockVOList() {
		List<BookVO> books = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			books.add(mockVO(i));
		}
		return books;
	}

	public Book mockEntity(Integer number) {
		Book book = new Book();
		book.setId(number.longValue());
		book.setTitle("Title" + number);
		book.setAuthor("Author" + number);

		int year = 1970 + number;
		try {
			book.setLaunchDate(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-01-01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		double price = number == 0 ? (number + 1) * 10 : number * 10;
		book.setPrice(price);

		return book;
	}

	public BookVO mockVO(Integer number) {
		BookVO book = new BookVO();
		book.setId(number.longValue());
		book.setTitle("Title" + number);
		book.setAuthor("Author" + number);

		int year = 1970 + number;
		try {
			book.setLaunchDate(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-01-01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		double price = number == 0 ? (number + 1) * 10 : number * 10;
		book.setPrice(price);

		return book;
	}

}
