package com.github.jmsmarcelo.exchange.head;

public class Inflation extends HttpConnect {
	public String get() {
		String date = super.getDate();
		return "Valor acumulado dos ultimos 12 meses até " + super.get(
				"https://brapi.dev/api/v2/inflation?country=brazil&historical=false&start=" +
				date + "&end=" + date + "&sortBy=date&sortOrder=desc"
				).replaceAll(".*date\\\":\\\"(\\d{2}/\\d{2}/\\d{4}).*value\\\":\\\"(\\d+\\.\\d+).*",
						"$1 é de $2% ");
	}
}
