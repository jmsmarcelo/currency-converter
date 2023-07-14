package com.github.jmsmarcelo.exchange.head;

import java.io.File;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Jose Marcelo (@JMSsMarcelo | @jmsmarcelo)
 */
public class Api {
	
	private String json;

	public String get(String code, String lab) {
		return jsonToMultiHashMap().get(code).get(lab);
	}
	public String get(String lab) {
		return jsonToSingleHashMap().get(lab);
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
	private HashMap<String, HashMap<String, String>> jsonToMultiHashMap() {
		String[] tempArr = json.replaceAll("^\\{\".*\":\\[\\{\"|\"\\}\\]\\}$", "").split("\"\\}\\,\\{\"");
		HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
		for(String i: tempArr) {
			HashMap<String, String> subData = new HashMap<String, String>();
			for(String j: i.split("\",\""))
				subData.put(j.split("\":\"")[0], j.split("\":\"")[1]);
			data.put(subData.get("fromCurrency") + "-" + subData.get("toCurrency"), subData);
		}
		return data;
	}
	private HashMap<String, String> jsonToSingleHashMap() {
		String[] tempArr = json.replaceAll("^\\{\".*\":\\[\\{\"|\"?\\}\\]\\}$", "").split("\",\"");
		HashMap<String, String> data = new HashMap<String, String>();
		for(String i: tempArr)
			data.put(i.split("\":\"")[0], i.split("\":\"?")[1]);
		return data;
	}
	
	protected void setDataFile(String fname) {
		try {
			if(this.json != null) {
				File myObj = new File(fname);
				FileWriter myObjWriter = new FileWriter(fname);
				if(!myObj.exists())
					myObj.createNewFile();
				myObjWriter.write(this.json);
				myObjWriter.close();
			}
		} catch (Exception e) {}
	}
	@SuppressWarnings("resource")
	protected void setJson(String fname) {
		File myObj = new File(fname);
		try {
			this.json = new Scanner(myObj).nextLine();
		} catch (Exception e) {}
	}
	protected String getDate() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}
