package com.github.jmsmarcelo.exchange.head;

import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author Jose Marcelo (@JMSsMarcelo | @jmsmarcelo)
 */
public class Api {
	
	private String json;

	public String get(String code, String lab) {
		String[] cd = code.split("-");
		String[][][] arr = jsonToArrMultiMoney();
		String result = "";
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				if(arr[i][0][1].matches(cd[0]) && arr[i][1][1].matches(cd[1]) && arr[i][j][0].matches(lab))
					return result = arr[i][j][1];
		return result;
	}
	public String get(String lab) {
		String[][] arr = jsonToArrSingleMoney();
		String result = "";
		for(int i = 0; i < arr.length; i++)
			if(arr[i][0].matches(lab))
				return result = arr[i][1];
		return result;
	}
	@SuppressWarnings("resource")
	public void set(URI uri) {
		try {
			HttpURLConnection conn = (HttpURLConnection)uri.toURL().openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("charset", "UTF-8");
			conn.connect();
			json = new Scanner(conn.getInputStream(), "UTF-8").nextLine();
			conn.disconnect();
		} catch(Exception e) {
			e.toString();
		}
	}
	private String[][][] jsonToArrMultiMoney() {
		String[] tempArr = json.replaceAll("^[\\{\".*\":.*\\{\"]|[\"\\}.*\\}]$", "").split("\"\\},.*\\{\"");
		String[][][] triArr = new String[tempArr.length]
				[tempArr[0].split("\",\"").length]
				[tempArr[0].split("\",\"")[0].split("\":\"").length];
		for(int i = 0; i < tempArr.length; i++)
			for(int j = 0; j < tempArr[i].split("\",\"").length; j++)
				triArr[i][j] = tempArr[i].split("\",\"")[j].split("\":\"");
		return triArr;
	}
	private String[][] jsonToArrSingleMoney() {
		String[] tempArr = json.replaceAll("^\\{\".*\":.*\\{\"|\"\\}.*\\}$", "").split("\",\"");
		String[][] biArr = new String[tempArr.length]
				[tempArr[0].split("\":\"").length];
		for(int i = 0; i < tempArr.length; i++)
			biArr[i] = tempArr[i].split("\":\"");
		return biArr;
	}
	protected String getDate() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}
