package com.java.web.core.lib.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONParser {

	public static JSONObject createJsonObject(String key, String value) {
		try {
			Map<String, String> odgovor = new HashMap<String, String>();
			odgovor.put(key, value);
			return new JSONObject(odgovor);
		}
		catch (Exception e) {
			return new JSONObject();
		}
	}

	public static JSONObject parseJsonObject(BufferedReader reader) {
		try {
			String line = reader.readLine();
			String objText = "";

			while (line != null) {

				System.out.println("Ucitavam iz reader-a: " + line);

				objText += line;
				line = reader.readLine();
			}

			reader.close();

			if (!objText.equals(""))
				return new JSONObject(objText);

			Map<String, String> data = new HashMap<String, String>();
			data.put("greska", "Klijent nije prosledio nikakve podatke");
			return new JSONObject(data);
		}
		catch (IOException e) {
			e.printStackTrace();
			Map<String, String> data = new HashMap<String, String>();
			data.put("greska", "Doslo je do greske prilikom citanja klijentove poruke na serveru");
			return new JSONObject(data);
		}
		catch (JSONException e) {
			e.printStackTrace();
			Map<String, String> data = new HashMap<String, String>();
			data.put("greska", "Doslo je do greske na serveru prilikom formatiranja podataka dobijenih od klijenta");
			return new JSONObject(data);
		}
	}
}
