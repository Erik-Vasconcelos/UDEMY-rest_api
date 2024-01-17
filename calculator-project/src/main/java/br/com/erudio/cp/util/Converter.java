package br.com.erudio.cp.util;

public class Converter {

	public Double convertToDouble(String number) {
		if (!isNumeric(number)) {
			throw new IllegalArgumentException("Please set a numeric number");
		}

		number = number.replaceAll(",", ".");
		
		return Double.parseDouble(number);
	}

	private boolean isNumeric(String numberString) {
		if (numberString == null || numberString.isBlank())
			return false;

		numberString = numberString.replaceAll(",", ".");

		return numberString.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
