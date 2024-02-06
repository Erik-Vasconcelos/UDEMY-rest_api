package br.com.erudio.cp.integrationtest.cors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonVO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String postalCode;
}