package br.com.erudio.cp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.cp.exceptions.UnsupportedMathOperationException;
import br.com.erudio.cp.util.Calculator;
import br.com.erudio.cp.util.Converter;

@RestController
public class MathController {

	private Converter converter = new Converter();

	@GetMapping("/sum/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		return Calculator.sum(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}

	@GetMapping("/subtraction/{numberOne}/{numberTwo}")
	public Double subtraction(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		return Calculator.substration(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}

	@GetMapping("/multiplication/{numberOne}/{numberTwo}")
	public Double multiplication(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return Calculator.multiplication(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}

	@GetMapping("/division/{numberOne}/{numberTwo}")
	public Double division(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return Calculator.division(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}

	@GetMapping("/average/{numberOne}/{numberTwo}")
	public Double average(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		return Calculator.average(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}

	@GetMapping("/square/{numberOne}")
	public Double square(@PathVariable(value = "numberOne") String number) throws Exception {
		return Calculator.square(converter.convertToDouble(number));
	}

}
