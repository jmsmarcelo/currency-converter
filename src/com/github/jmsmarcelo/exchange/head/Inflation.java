package com.github.jmsmarcelo.exchange.head;

import java.io.IOException;
import java.net.URISyntaxException;

public class Inflation extends HttpConnect {
	
	public String get() throws IOException, URISyntaxException {
		String date = super.getDate();
		String url = "https://brapi.dev/api/v2/inflation?country=brazil&historical=false&start=" +
				date + "&end=" + date + "&sortBy=date&sortOrder=desc";

		return "Valor acumulado dos ultimos 12 meses até " + super.get(url).
				replaceAll(".*date\\\":\\\"(\\d{2}/\\d{2}/\\d{4}).*value\\\":\\\"(\\d+\\.\\d+).*",
						"$1 é de $2% ");
	}
}
