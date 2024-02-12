package br.com.erudio.cp.data.vo_v1.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountCredentialsVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;

}
