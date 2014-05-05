package com.banditUI.articleViewer.system;

import java.awt.*;
import javax.swing.BorderFactory; 
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.ArrayList;

class Display extends JFrame {
	private int MAX_ROWS = 4, MAX_COLS = 2;

	public Display() {
		setTitle("Dummy display");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setupInterface();
	}
	
	public void setupInterface() {
		ArrayList<Result> results = Result.dummyResults(16);
    	for (Result r : results) {
    		System.out.println(r.tempTitle + " => " + r.getConfidence());
    	}
    	setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	c.weightx = 0.5;
    	c.weighty = 1/8.0;
    	c.gridx = 0;
    	c.gridy = 0;
    	c.fill = GridBagConstraints.BOTH;
    	for (Result r : results) {
    		addResult(r, c);
    	}
	}

	public void addResult(Result r, GridBagConstraints c) {
		int weight = 1;
		if (r.getConfidence() >= 0.5) weight = 4;
		else if (r.getConfidence() >= 0.25) weight = 2;
		if (c.gridy <= MAX_ROWS - weight && c.gridx < MAX_COLS) {
			c.gridheight = weight;
			add(resultPanel(r), c);
			c.gridy += weight;
		}
		if (c.gridy >= MAX_ROWS) {
			c.gridy = 0;
			c.gridx++;
		}
	}

	public JPanel resultPanel(Result r) {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		TitledBorder tb = BorderFactory.createTitledBorder(blackline, "Topic");
		JPanel pane = new JPanel();
		pane.setBorder(tb);
		pane.add(new JLabel(String.format("%s (%.2f)", r.tempTitle, r.getConfidence())));
		return pane;
	}
}