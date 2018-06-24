package com.orange.plump.ComboGrabber.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.orange.plump.ComboGrabber.App;

import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public JTextArea 
	COMBO_FIELD,
	SEARCH_TERMS_FIELD,
	OUTPUT_FIELD,
	THREAD_COUNTER,
	COMBO_COUNTER;
	
	private JTextField txtSearchTerms;
	private JTextField txtComboList;
	private JTextField txtThreadsActive;
	private JButton btnSaveCombos;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		setResizable(false);
		initialize();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 650, 400);
		this.setTitle("Combo Grabber ~ PlumpOrange");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		COMBO_FIELD = new JTextArea();
		COMBO_FIELD.setBorder(null);
		COMBO_FIELD.setBounds(316, 23, 291, 304);
		COMBO_FIELD.setEditable(false);
		
		SEARCH_TERMS_FIELD = new JTextArea();
		SEARCH_TERMS_FIELD.setBorder(null);
		SEARCH_TERMS_FIELD.setBounds(35, 23, 249, 136);
		SEARCH_TERMS_FIELD.setEditable(true);
		
		THREAD_COUNTER = new JTextArea();
		THREAD_COUNTER.setText("0");
		THREAD_COUNTER.setBorder(null);
		THREAD_COUNTER.setBounds(257, 170, 27, 23);
		THREAD_COUNTER.setEditable(false);
		getContentPane().add(THREAD_COUNTER);
		
		COMBO_COUNTER = new JTextArea();
		COMBO_COUNTER.setBackground(Color.LIGHT_GRAY);
		COMBO_COUNTER.setText("0");
		COMBO_COUNTER.setBorder(null);
		COMBO_COUNTER.setBounds(557, 0, 50, 23);
		COMBO_COUNTER.setEditable(false);
		getContentPane().add(COMBO_COUNTER);
		
		JScrollPane COMBO_FIELD_SCROLL = new JScrollPane(COMBO_FIELD);
		COMBO_FIELD_SCROLL.setBounds(316, 23, 291, 304);
		COMBO_FIELD_SCROLL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		COMBO_FIELD_SCROLL.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().add(COMBO_FIELD_SCROLL);
		
		JScrollPane SEARCH_TERMS_FIELD_SCROLL = new JScrollPane(SEARCH_TERMS_FIELD);
		SEARCH_TERMS_FIELD_SCROLL.setBounds(35, 23, 249, 136);
		SEARCH_TERMS_FIELD_SCROLL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		SEARCH_TERMS_FIELD_SCROLL.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(SEARCH_TERMS_FIELD_SCROLL);

		JCheckBox chckbxIgnoreDuplicates = new JCheckBox("Ignore Duplicates");
		chckbxIgnoreDuplicates.setBackground(Color.LIGHT_GRAY);
		chckbxIgnoreDuplicates.setBounds(316, 334, 154, 23);
		chckbxIgnoreDuplicates.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if (App.toIgnoreDupes) App.toIgnoreDupes = false;
				else App.toIgnoreDupes = true;
			}
		});
		getContentPane().add(chckbxIgnoreDuplicates);
		
		txtSearchTerms = new JTextField();
		txtSearchTerms.setText("Search Terms");
		txtSearchTerms.setBorder(null);
		txtSearchTerms.setBackground(Color.LIGHT_GRAY);
		txtSearchTerms.setEditable(false);
		txtSearchTerms.setBounds(35, 0, 249, 20);
		getContentPane().add(txtSearchTerms);
		txtSearchTerms.setColumns(10);
		
		txtComboList = new JTextField();
		txtComboList.setBorder(null);
		txtComboList.setBackground(Color.LIGHT_GRAY);
		txtComboList.setEditable(false);
		txtComboList.setText("Combo List");
		txtComboList.setBounds(316, 0, 291, 20);
		getContentPane().add(txtComboList);
		txtComboList.setColumns(10);

		JButton btnStart = new JButton("Start");
		btnStart.setBounds(35, 324, 114, 23);
		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				App.start();
			}
		});
		getContentPane().add(btnStart);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(170, 324, 114, 23);
		btnStop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				App.stop();
			}
		});
		getContentPane().add(btnStop);
		
		OUTPUT_FIELD = new JTextArea();
		OUTPUT_FIELD.setEditable(false);
		OUTPUT_FIELD.setBackground(Color.GRAY);
		OUTPUT_FIELD.setBorder(null);
		OUTPUT_FIELD.setBounds(35, 290, 249, 23);
		getContentPane().add(OUTPUT_FIELD);
		
		JLabel BACKGROUND = new JLabel("");
		BACKGROUND.setBorder(null);
		try {
			BACKGROUND.setIcon(new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/tEGJZaG.png").openConnection().getInputStream())));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		txtThreadsActive = new JTextField();
		txtThreadsActive.setBorder(null);
		txtThreadsActive.setText("Threads Active");
		txtThreadsActive.setBackground(Color.LIGHT_GRAY);
		txtThreadsActive.setBounds(161, 170, 86, 20);
		getContentPane().add(txtThreadsActive);
		txtThreadsActive.setColumns(10);
		
		btnSaveCombos = new JButton("Save Combos");
		btnSaveCombos.setBounds(493, 334, 114, 23);
		btnSaveCombos.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				App.createComboFile();
			}
			
		});
		getContentPane().add(btnSaveCombos);
		BACKGROUND.setBounds(0, 0, 634, 361);
		getContentPane().add(BACKGROUND);
	}
}
