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

import br.com.erudio.cp.data.vo_v1.BookVO;
import br.com.erudio.cp.service.BookService;
import br.com.erudio.cp.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoint for managing book") // Descrição do recurso
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML })
	@Operation(summary = "Finds a book", description = "Finds a book", tags = { "Book" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unaouthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Foud", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<BookVO> getBook(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
	@Operation(summary = "Finds all books", description = "Finds all book", tags = { "Book" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookVO.class)))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unaouthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Foud", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<List<BookVO>> getBooks() {
		return ResponseEntity.status(HttpStatus.OK).body(bookService.findAll());
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YAML })
	@Operation(summary = "Insert book", description = "Insert book", tags = { "Book" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unaouthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Foud", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<BookVO> insert(@RequestBody BookVO book) {
		return ResponseEntity.status(HttpStatus.OK).body(bookService.insert(book));
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YAML })
	@Operation(summary = "Update book", description = "Update book", tags = { "Book" }, responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = BookVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unaouthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Foud", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<BookVO> update(@PathVariable(name = "id") Long id, @RequestBody BookVO book) {
		return ResponseEntity.status(HttpStatus.OK).body(bookService.update(id, book));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete a book", description = "Delete a book", tags = { "Book" }, responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unaouthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Foud", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
