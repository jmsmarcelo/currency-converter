package com.github.jmsmarcelo.exchange.head;

import java.net.URI;

public class Selic extends Api {
	public Selic() {
		String date = super.getDate();
		try {
			super.set(new URI("https://brapi.dev/api/v2/prime-rate?country=brazil&historical=false&start=" +
					date + "&end=" + date + "&sortBy=date&sortOrder=desc"));
			super.setDataFile("prime-rate.json");
		} catch (Exception e) {}
	}
	@Override
	public String get(String lab) {
		return super.get(lab);
	}
	@Override
	public void setJson(String fname) {
		super.setJson(fname);
	}
}
