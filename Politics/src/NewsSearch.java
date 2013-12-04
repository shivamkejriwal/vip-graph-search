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
		obj = load("http://feeds.feedburner.com/ndtv/Lsgd",10,true);
		System.out.println(obj.toString());
		obj = searchInlLoad(query,10,false);
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
	
	public JSONObject load (String feedUrl,int num, boolean includeHistoricalEntries) throws Exception{
		String scoring = "";
		if(includeHistoricalEntries){
			scoring = "&scoring=h";
		}
		URL url = new URL("https://ajax.googleapis.com/ajax/services/feed/load?v=1.0"+scoring+"&q="+feedUrl+"&num="+num);
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

	
	public JSONObject searchInlLoad(String query,int num, boolean includeHistoricalEntries) throws Exception{
		String feedURL="https://news.google.com/news/feeds?pz=1&cf=all&ned=en&hl=en&q="+URLEncoder.encode(query, "UTF-8")+"&output=rss";
		String scoring = "";
		if(includeHistoricalEntries){
			scoring = "&scoring=h";
		}
		URL url = new URL("https://ajax.googleapis.com/ajax/services/feed/load?v=1.0"+scoring+"&q="+URLEncoder.encode(feedURL, "UTF-8")+"&num="+num);
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
//https://news.google.com/news/feeds?pz=1&cf=all&ned=en&hl=en&q=Social+Media&output=rss
//http://www.google.co.in/elections/ed/in
//google advance news search
}
