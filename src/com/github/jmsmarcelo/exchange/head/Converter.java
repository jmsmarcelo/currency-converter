package com.github.jmsmarcelo.exchange.head;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;

public class Converter extends Api {
	public Converter(String code) {
		try {
			super.set(new URI("https://brapi.dev/api/v2/currency?currency=" + code));
		} catch (URISyntaxException e) {
			e.toString();
		}
	}
	public String getCurrency(double m) {
		return getSymbol(super.get("toCurrency")) + (
				new BigDecimal(super.get("bidPrice")).
						multiply(BigDecimal.valueOf(m)).setScale(2, RoundingMode.CEILING)
						.toString().toString().replaceAll(",", ".").replaceAll("\\.(\\d+)$", ",$1")
		);
	}
	@Override
	public String get(String lab) {
		return super.get(lab);
	}

	private String getSymbol(String gs) {
		if(gs.matches("BRL")) return "R$ ";
		else if(gs.matches("GBP")) return "£ ";
		else if(gs.matches("EUR")) return "€ ";
		else return "$ ";
	}
}
