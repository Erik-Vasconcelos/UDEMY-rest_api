package br.com.erudio.cp.data.vo_v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonVO {
	private Long id;
	private String firstName;
	private String lastName;
	private String postalCode;
}