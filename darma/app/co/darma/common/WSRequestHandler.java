package co.darma.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import co.darma.exceptions.InternalException;

public class WSRequestHandler {

	public static String sendGetRequest(String urlString)
			throws InternalException {
		try {
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line + "\n");
				line = br.readLine();
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			throw new InternalException("Failed to connect to url - "
					+ urlString, e);
		}
	}

}
