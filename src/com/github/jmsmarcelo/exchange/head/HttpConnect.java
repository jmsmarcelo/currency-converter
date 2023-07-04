package com.github.jmsmarcelo.exchange.head;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HttpConnect {
	protected String get(String url) {
		try {
			URI uri = new URI(url);
			HttpURLConnection conn = (HttpURLConnection)uri.toURL().openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("charset", "UTF-8");
			conn.connect();
			return streamToString(conn.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("resource")
	private String streamToString(InputStream inSt) {
		return new Scanner(inSt, "UTF-8").useDelimiter("\\Z").next();
	}
	protected String getDate() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}
