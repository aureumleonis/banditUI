package com.banditUI.articleViewer.system;

import java.awt.*;
import javax.swing.BorderFactory; 
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Calendar;
import java.util.Map.Entry;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.awt.event.*;

class Display extends JFrame implements WindowListener {
	private int MAX_ROWS = 8, MAX_COLS = 1;
	private Semaphore main_lock;

	public Display() {
		setTitle("Dummy display");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setupInterface();
	}

	public Display(Calendar day, List<Entry<Article, Integer>> display, Semaphore lock) {
		main_lock = lock;
		try {
			lock.acquire();
		}
		catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);
		setTitle(day.getTime().toString());
		setSize(800, 600);
		setLocationRelativeTo(null);
		setupInterface(display);
	}
	
	public void setupInterface(List<Entry<Article, Integer>> display) {
    	setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	c.weightx = 0.5;
    	c.gridx = 0;
    	c.gridy = 0;
    	c.fill = GridBagConstraints.BOTH;
    	for (Entry<Article, Integer> r : display) {
    		addResult(r, c);
    	}
	}

	public void addResult(Entry<Article, Integer> r, GridBagConstraints c) {
		int weight = r.getValue();
		if (weight < 1) return;
		System.out.println("Adding art sz="+weight);
		if (c.gridy <= MAX_ROWS - weight && c.gridx < MAX_COLS) {
			c.gridheight = weight;
			System.out.format("Placing at y=%d, dy=%d\n",c.gridy,c.gridheight);
			c.weighty = (1/(double)MAX_ROWS)*weight;
			add(resultPanel(r), c);
			c.gridy += weight;
		}
		if (c.gridy >= MAX_ROWS) {
			c.gridy = 0;
			c.gridx++;
		}
	}

	public JPanel resultPanel(Entry<Article, Integer> r) {
		Article ar = r.getKey();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		TitledBorder tb = BorderFactory.createTitledBorder(blackline, ar.getTopic());
		JPanel pane = new JPanel();
		pane.setBorder(tb);
		pane.add(new JLabel(ar.getTitle()));
		return pane;
	}

	public void windowClosed(WindowEvent arg0) {
        main_lock.release();
    }

    public void windowActivated(WindowEvent arg0) {}

    public void windowClosing(WindowEvent arg0) {}

    public void windowDeactivated(WindowEvent arg0) {}

    public void windowDeiconified(WindowEvent arg0) {}

    public void windowIconified(WindowEvent arg0) {}

    public void windowOpened(WindowEvent arg0) {}

}