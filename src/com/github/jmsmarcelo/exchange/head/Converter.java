package com.github.jmsmarcelo.exchange.head;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;

public class Converter extends Api {
	public Converter() {
		try {
			super.set(new URI("https://brapi.dev/api/v2/currency?currency=BRL-USD,BRL-EUR,BRL-GBP,BRL-ARS,BRL-CLP,USD-BRL,EUR-BRL,GBP-BRL,ARS-BRL,CLP-BRL"));
			super.setDataFile("currency.json");
		} catch (Exception e) {}
	}
	public String getCurrency(String code, double m) {
		return getSymbol(super.get(code, "toCurrency")) + (
				new BigDecimal(super.get(code, "bidPrice")).
						multiply(BigDecimal.valueOf(m)).setScale(2, RoundingMode.CEILING)
						.toString().toString().replaceAll(",", ".").replaceAll("\\.(\\d+)$", ",$1")
		);
	}
	@Override
	public String get(String code, String lab) {
		return super.get(code, lab);
	}
	@Override
	public void setJson(String fname) {
		super.setJson(fname);
	}

	private String getSymbol(String gs) {
		if(gs.matches("BRL")) return "R$ ";
		else if(gs.matches("GBP")) return "£ ";
		else if(gs.matches("EUR")) return "€ ";
		else return "$ ";
	}
}
