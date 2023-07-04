package com.github.jmsmarcelo.exchange.head;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Converter extends HttpConnect {
	private BigDecimal currencyValue;

	public String getCurrency(double m, String cT) {
		this.setCurrency(cT);
		return "O valor da conversão é de " + getSymbol(cT) + (
				new DecimalFormat("#,###,##0.00").format(
				Double.parseDouble(currencyValue.multiply(BigDecimal.valueOf(m)).
						setScale(2, RoundingMode.CEILING).toString())).
				toString().replaceAll(",", ".").replaceAll("\\.(\\d{1,})$", ",$1")
		);
	}

	private void setCurrency(String cT) {
		currencyValue = new BigDecimal(
				super.get("https://brapi.dev/api/v2/currency?currency=" + cT).
				replaceAll(".*askPrice\\\":\\\"(\\d+\\.\\d+).*", "$1")
		);
	}
	private String getSymbol(String gs) {
		if(gs.matches(".*BRL$")) return "R$ ";
		else if(gs.matches(".*[USD|ARS|CLP]$")) return "$ ";
		else if(gs.matches(".*EUR$")) return "€ ";
		else return "£ ";
	}
}
