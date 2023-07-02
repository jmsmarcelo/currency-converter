package com.github.jmsmarcelo.exchange.head;

import java.io.IOException;
import java.net.URISyntaxException;

public class Selic extends HttpConnect {
	
	public String get() throws IOException, URISyntaxException {
		String date = super.getDate();
		String url = "https://brapi.dev/api/v2/prime-rate?country=brazil&historical=false&start=" +
				date + "&end=" + date + "&sortBy=date&sortOrder=desc";

		return "O valor está em " + super.get(url).replaceAll(".*value\\\":\\\"(\\d+\\.\\d+).*", "$1") + "% ao ano";
	}
}
