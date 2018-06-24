package com.orange.plump.ComboGrabber;

import java.awt.Color;
import java.awt.Cursor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.orange.plump.ComboGrabber.ui.MainWindow;

public class App {
	
	public static MainWindow MainFrame;
	
	public static boolean toIgnoreDupes = false;
	public static boolean hasStarted = false;
	
	public static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	
	public static List<SearchThread> ThreadsSearching = new ArrayList<SearchThread>();
	public static List<String> 
	CurrentComboList = new ArrayList<String>(),
	CurrentLinkList = new ArrayList<String>();
	
	public static void main(String[] args) {
		App.MainFrame = new MainWindow();
		
		while (true) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			update();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void update() {
		App.MainFrame.THREAD_COUNTER.setText(String.valueOf(App.ThreadsSearching.size()));
		App.MainFrame.COMBO_COUNTER.setText(String.valueOf(App.CurrentComboList.size()));
		if (App.ThreadsSearching.size() == 0 & hasStarted) {
			String[] newCombo = new String[App.CurrentComboList.size()];
			for (int i = 0; i < newCombo.length; i++) {
				String combo = App.CurrentComboList.get(i);
				boolean isThere = false;
				for (int j = 0; j < i; j++) {
					String current = newCombo[j];
					if (current != null)
					if (newCombo[j].equals(combo)) {
						isThere = true;
						break;
					}
				}
				if (!isThere) newCombo[i] = combo;
			}
			
			App.CurrentComboList.clear();
			String combos = "";
			for (String combo : newCombo) if (combo != "" && combo != null) {
				App.CurrentComboList.add(combo);
				combos += combo + "\n";
			}
			App.MainFrame.COMBO_COUNTER.setText(String.valueOf(App.CurrentComboList.size()));
			
			
			App.MainFrame.COMBO_FIELD.setText(combos);
			MainFrame.setCursor(Cursor.DEFAULT_CURSOR);
			hasStarted = false;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void start() {
		MainFrame.setCursor(Cursor.WAIT_CURSOR);
		MainFrame.OUTPUT_FIELD.setText("Starting... ");
		MainFrame.OUTPUT_FIELD.setForeground(Color.GREEN);
		
		System.setProperty("http.agent", App.USER_AGENT);
		Document doc = null;
		
		System.out.println(App.MainFrame.SEARCH_TERMS_FIELD.getText());
		
		try {
			doc = Jsoup.connect("https://www.google.com/search?q=PasteBin+" + App.MainFrame.SEARCH_TERMS_FIELD.getText().replaceAll(" ", "").replaceAll("\n", "+")).userAgent(App.USER_AGENT).get();
		} catch (Exception e) {
			MainFrame.OUTPUT_FIELD.setText("An Error Has Occured! Try Again");
			MainFrame.OUTPUT_FIELD.setForeground(Color.RED);
			e.printStackTrace();
			return;
		}
		MainFrame.OUTPUT_FIELD.setText("Started");
		MainFrame.OUTPUT_FIELD.setForeground(Color.GREEN);
		
		int amount = 0;
        if (doc != null && amount < 25) for (Element result : doc.select("h3.r a")) {
        	
            final String url = result.attr("href");
            
            if (url.toLowerCase().contains("pastebin")) {
            	if (!App.CurrentLinkList.contains(url)) {
            		App.CurrentLinkList.add(url);
            		SearchThread search = new SearchThread(url);
            		
            		App.ThreadsSearching.add(search);
            		
            		search.start();
            		amount++;
            	}
            }
            
        }
        hasStarted = true;
	}
	
	public static void stop() {
		for (SearchThread search : App.ThreadsSearching) {
			search.stopSearch();
			App.ThreadsSearching.remove(search);
		}
		MainFrame.OUTPUT_FIELD.setText("Stopped");
		MainFrame.OUTPUT_FIELD.setForeground(Color.RED);
	}
	
	public static void sendCombo(String combo) {
		App.CurrentComboList.add(combo);
	}
	
	public static void createComboFile() {
		File file = new File(System.getProperty("user.dir") + "/Combo.txt");
		try {
			if (!file.exists())file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (String combo : App.CurrentComboList)
			try {
				p(fw, combo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void p(FileWriter fw, String s) throws IOException {
		fw.write(s + "\r\n");
	}
	
}
