package com.banditUI.articleViewer.system;

import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) throws IOException {  
    	Calendar startDate = Calendar.getInstance();
    	Calendar endDate = Calendar.getInstance();
    	startDate.set(2009, Calendar.MAY, 7);
    	endDate.set(2014, Calendar.MARCH, 9);
    	endDate.set(Calendar.AM_PM, Calendar.PM);
    	startDate.set(Calendar.AM_PM, Calendar.AM);
    	
    	WordCounter.CalculateAllArticleFVs("F:/BanditProjcet/Articles/", 
    			"F:/BanditProject/FeatureVectors/", startDate, endDate);
    	/*
    	Dictionaries d = Dictionaries.getInstance();
    	ArticleSource s = new ArticleSource();
    	Person billy = new Person(true);
    	Calendar date = Calendar.getInstance();
    	date.set(2013, 6, 1);
    	Exp4Simulator sim = new Exp4Simulator(billy, 15, s, 8, .5);
    	sim.runSimulationOneDay(date);
    	*/
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

