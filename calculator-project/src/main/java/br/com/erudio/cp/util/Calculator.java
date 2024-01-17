package br.com.erudio.cp.util;

public class Calculator {

	public static Double sum(double numberOne, double numberTwo) {
		return numberOne + numberTwo;
	}

	public static Double substration(double numberOne, double numberTwo) {
		return numberOne - numberTwo;
	}

	public static Double multiplication(double numberOne, double numberTwo) {
		return numberOne * numberTwo;
	}

	public static Double division(double numberOne, double numberTwo) {
		return numberOne / numberTwo;
	}

	public static Double average(double numberOne, double numberTwo) {
		return (numberOne + numberTwo) / 2;
	}

	public static Double square(double number) {
		return Math.sqrt(number);
	}

}
