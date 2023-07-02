package com.github.jmsmarcelo.exchange.head;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;

public class Converter extends HttpConnect {
	private BigDecimal currencyValue;
	
	public String getCurrency(double m, String cT) {
		cT = setTypeConversion(cT);
		try {
			this.setCurrency(cT);
		} catch (URISyntaxException | IOException e) {
			
			e.printStackTrace();
		}
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		return "O valor da conversão é de " + getSymbol(cT) + (df.format(
				Double.parseDouble(currencyValue.multiply(
					BigDecimal.valueOf(m)).setScale(2, RoundingMode.CEILING).toString()
				)
			).toString().replaceAll(",", ".").replaceAll("\\.(\\d{1,})$", ",$1")
		);
	}
	
	private void setCurrency(String cT) throws URISyntaxException, MalformedURLException, IOException {
		String url = "https://brapi.dev/api/v2/currency?currency=" + cT;

		currencyValue = new BigDecimal(
				super.get(url).replaceAll(".*askPrice\\\":\\\"(\\d+\\.\\d+).*", "$1")
		);
	}

	private String getSymbol(String gs) {
		String symbol = "";
		if(gs.matches(".*BRL$")) symbol = "R$ ";
		else if(gs.matches(".*[USD|ARS|CLP]$")) symbol = "$ ";
		else if(gs.matches(".*EUR$")) symbol = "€ ";
		else if(gs.matches(".*GBP$")) symbol = "£ ";
		return (symbol);
	}
	private String setTypeConversion(String tc) {
		//USD-BRL,EUR-BRL,GBP-BRL,ARS-BRL,CLP-BRL
		if(tc == "De Reais para Dólares") return "BRL-USD";
		else if(tc == "De Reais para Euros") return "BRL-EUR";
		else if(tc == "De Reais para Libras Esterlinas") return "BRL-GBP";
		else if(tc == "De Reais para Pesos Argentinos") return "BRL-ARS";
		else if(tc == "De Reais para Pesos Chilenos") return "BRL-CLP";
		else if(tc == "De Dólares para Reais") return "USD-BRL";
		else if(tc == "De Euros para Reais") return "EUR-BRL";
		else if(tc == "De Libras Esterlinas para Reais") return "GBP-BRL";
		else if(tc == "De Pesos Argentinos para Reais") return "ARS-BRL";
		else return "CLP-BRL";
	}
}
