package br.com.erudio.cp.data.vo_v1;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookVO extends RepresentationModel<BookVO> {

	private Long id;
	private String title;
	private String author;
	private Date launchDate;
	private double price;
	
}