package br.com.erudio.cp.data.vo_v2;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonVOV2 {
	private Long id;
	private String firstName;
	private String lastName;
	private String postalCode;
	private LocalDate birtDay;
}