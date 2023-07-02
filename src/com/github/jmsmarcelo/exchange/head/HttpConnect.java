package com.github.jmsmarcelo.exchange.head;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HttpConnect {
	
	protected String get(String s) throws IOException, URISyntaxException {
		URI url = new URI(s);
		HttpURLConnection conn = (HttpURLConnection)url.toURL().openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("charset", "UTF-8");
		conn.connect();
		String result = streamToString(conn.getInputStream());
		conn.disconnect();
		return result;
	}
	@SuppressWarnings("resource")
	private String streamToString(InputStream inSt) {
		return new Scanner(inSt, "UTF-8").useDelimiter("\\Z").next();
	}
	protected String getDate() {
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return today.format(formatDate);
	}
}
