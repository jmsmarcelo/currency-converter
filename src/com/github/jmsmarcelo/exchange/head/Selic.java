package com.github.jmsmarcelo.exchange.head;

public class Selic extends HttpConnect {
	public String get() {
		String date = super.getDate();
		return "O valor está em " + super.get(
					"https://brapi.dev/api/v2/prime-rate?country=brazil&historical=false&start=" +
					date + "&end=" + date + "&sortBy=date&sortOrder=desc"
				).replaceAll(".*value\\\":\\\"(\\d+\\.\\d+).*", "$1") + "% ao ano";
	}
}
