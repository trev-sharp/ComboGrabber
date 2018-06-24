package com.orange.plump.ComboGrabber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SearchThread extends Thread {
	private String url;
	private final static String startWith = "		<textarea id=\"paste_code\" class=\"paste_code\" name=\"paste_code\" onkeydown=\"return catchTab(this,event)\">";
	private final static String endWith = "</textarea>";
	
	private boolean doSearch = true;
	
	public SearchThread(String url) {
		this.url = url;
		System.out.println(url);
	}
	
	public void run() {
			URL urlWeb = null;
			try {
				urlWeb = new URL(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			URLConnection connection = null;
			try {
				connection = urlWeb.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InputStream is = null;
			try {
				is = connection.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String s = null;
			boolean isText = false;
			try {
				while((s = br.readLine()) != null) {
					if (!doSearch) return;
					if (s.startsWith(startWith)) {
						isText = true;
						format(s.replace(startWith, ""));
					} else if (s.endsWith(endWith)) {
						isText = false;
						format(s.replace(endWith, ""));
					} else if (isText == true) format(s);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			App.ThreadsSearching.remove(this);
	}
	
	public static void format(String s) throws IOException {
		if (!s.contains("@")) return;
		if (s.contains("|")) s = s.replace("|", ":");
		if (s.contains(" ")) s = s.replace(" ", "");
		if (!s.contains(":")) return;
		if (s.contains("{NewLine}")) s = s.replace("{NewLine}", "");
		if (App.toIgnoreDupes) if (App.CurrentComboList.contains("s")) return;
		
		App.sendCombo(s);
	}
	
	@SuppressWarnings("deprecation")
	public void stopSearch() {
		this.doSearch = false;
		this.stop();
	}
}
