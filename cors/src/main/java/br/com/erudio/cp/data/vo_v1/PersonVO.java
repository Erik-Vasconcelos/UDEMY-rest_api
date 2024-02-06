package br.com.erudio.cp.data.vo_v1;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonVO extends RepresentationModel<PersonVO>{
	
	private Long id;
	private String firstName;
	private String lastName;
	private String postalCode;
}