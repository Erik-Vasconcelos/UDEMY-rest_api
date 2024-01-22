package br.com.erudio.cp.data.vo_v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({"first_name", "lastName", "postalCode", "id"})
public class PersonVO {
	
	private Long id;
	
	@JsonProperty("first_name")
	private String firstName;
	private String lastName;
	
	@JsonIgnore
	private String postalCode;
}