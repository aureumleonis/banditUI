package com.banditUI.articleViewer.system;

import java.util.Calendar;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {  

    	Dictionaries d = Dictionaries.getInstance();
    	ArticleSource s = new ArticleSource();
    	Person billy = new Person(true);
    	Calendar date = Calendar.getInstance();
    	date.set(2013, 6, 1);
    	Exp4Simulator sim = new Exp4Simulator(billy, 15, s, 8, .5);
    	sim.runSimulationOneDay(date);
    	/*
		System.out.println("Dummy results");
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Display disp = new Display();
                disp.setVisible(true);
            }
        });
        */
    	
    	
    }

}

