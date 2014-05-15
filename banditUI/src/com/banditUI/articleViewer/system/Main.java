package com.banditUI.articleViewer.system;

import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) throws IOException {  
    	/*Calendar startDate = Calendar.getInstance();
    	Calendar endDate = Calendar.getInstance();
    	startDate.set(2009, Calendar.MAY, 7);
    	endDate.set(2014, Calendar.MARCH, 9);
    	endDate.set(Calendar.AM_PM, Calendar.PM);
    	startDate.set(Calendar.AM_PM, Calendar.AM);
    	
    	WordCounter.CalculateAllArticleFVs("F:/BanditProjcet/Articles/", 
    			"F:/BanditProject/FeatureVectors/", startDate, endDate);
    	*/
    	
    	Dictionaries d = Dictionaries.getInstance();
    	ArticleSource s = new ArticleSource("F:/BanditProject/");
    	Calendar dateBilly = Calendar.getInstance();
    	dateBilly.set(2010, Calendar.MAY, 7);
    	Calendar dateJoe = Calendar.getInstance();
    	dateJoe.set(2010, Calendar.MAY, 7);
    	Calendar dateDan = Calendar.getInstance();
    	dateDan.set(2010, Calendar.MAY, 7);
    	Calendar dateChris = Calendar.getInstance();
    	dateChris.set(2010, Calendar.MAY, 7);
    	Thread[] t = new Thread[4];
    	t[0] = new Thread(new RunnableSimulator("Billy", false, 'e', 13, 25, 75, .9, 10, s, dateBilly));
    	t[1] = new Thread(new RunnableSimulator("Joe", false, 'e', 13, 25, 75, .9, 15, s, dateJoe));
    	t[2] = new Thread(new RunnableSimulator("Dan", false, 'e', 13, 25, 75, .9, 25, s, dateDan));
    	t[3] = new Thread(new RunnableSimulator("Chris", false, 'e', 13, 25, 75, .9, 35, s, dateChris));
    	
    	for (int i = 0; i < 4; i++)
    		t[i].start();
    	for (int i = 0; i < 4; i++) {
    		try { t[i].join(); }
    		catch(InterruptedException e) {System.out.println("Error, interrupted");}
    	}
    	
    	
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

