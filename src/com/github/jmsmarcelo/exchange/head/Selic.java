package com.github.jmsmarcelo.exchange.head;

import java.net.URI;
import java.net.URISyntaxException;

public class Selic extends Api {
	public Selic() {
		String date = super.getDate();
		try {
			super.set(new URI("https://brapi.dev/api/v2/prime-rate?country=brazil&historical=false&start=" +
					date + "&end=" + date + "&sortBy=date&sortOrder=desc"));
		} catch (URISyntaxException e) {
			e.toString();
		}
	}
	@Override
	public String get(String lab) {
		return super.get(lab);
	}
}
