import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;


public class NewsSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new NewsSearch("Rahul Gandhi");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public NewsSearch(String query) throws Exception{
		JSONObject obj = find(query);
		System.out.println(obj.toString());
		obj = load("http://ndtv.com",10);
		System.out.println(obj.toString());
	}
	public JSONObject find (String query) throws Exception{
		URL url = new URL("https://ajax.googleapis.com/ajax/services/feed/find?" +
                "v=1.0&q="+URLEncoder.encode(query, "UTF-8")+"&userip="+Inet4Address.getLocalHost().getHostAddress());
		System.out.println("URL:"+url);
		URLConnection connection = url.openConnection();
		connection.addRequestProperty("Referer", "http://www.youthdemocracy.in");
		
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while((line = reader.readLine()) != null) {
		builder.append(line);
		}
		
		JSONObject json = new JSONObject(builder.toString());
		return json;
	}
	
	public JSONObject load (String feedUrl,int num) throws Exception{
		URL url = new URL("https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q="+feedUrl+"&num="+num);
		System.out.println("URL:"+url);
		URLConnection connection = url.openConnection();
		connection.addRequestProperty("Referer", "http://www.youthdemocracy.in");
		
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while((line = reader.readLine()) != null) {
		builder.append(line);
		}
		
		JSONObject json = new JSONObject(builder.toString());
		return json;
	}
}
