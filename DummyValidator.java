package com.insags.framework.test.dummy;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Clase DummyValidator.<br>
 * @author INSA
 */
public class DummyValidator implements Validator {

	/** 
	 * M&eacute;todo sobrescrito supports.<br>
	 * @param clase La clase.
	 * @return Valor de retorno.
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	
	public boolean supports(Class<?> clase) {
		return false;
	}

	/** 
	 * M&eacute;todo sobrescrito validate.<br>
	 * @param objeto El objeto.
	 * @param errors Los errores.
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	
	public void validate(Object objeto, Errors errors) {
		
	}

}
