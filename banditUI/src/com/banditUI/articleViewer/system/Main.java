package com.banditUI.articleViewer.system;

import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) throws IOException {  
    	
    	Calendar date = Calendar.getInstance();
    	date.set(2013, Calendar.JANUARY, 3);
    	
    	ArticleSource s = new ArticleSource(args[0]);
    	Article[] articles = s.getArticlesForDate(date);
    	
    	double[] chris_disps = {.9, .9, .9, .9, .1, .1, .1, .1};
    	double[] dan_disps = {.1,.1,.1,.1,.9,.9,.1,.1};
    	
    	// Person billy = new Person(true);
    	// Person joe = new Person(true);
    	// Person chris = new Person(true);
    	Person dan = new Person(dan_disps, 0.25, 0.8);
    	
    	double[] e1Disps = {.1,.1,.1,.1,.9,.9,.1,.1};
    	Expert e1 = new Expert(new Context(e1Disps), 0.25, 0.8);
    	
    	Expert[] exps = new Expert[10];
    	exps[0] = e1;
    	for (int i = 1; i < 10; i++) 
    		exps[i] = new Expert();
    	
    	//ExpertSimulator sim = new ExpertSimulator(dan, exps, s, date, 0, 0.9);
        ExpertSimulator sim = new ExpertSimulator(dan, 20, s, date, 0, 0.9);
        ArrayList<Double> ctrs = new ArrayList<Double>();
        ArrayList<Double> ts = new ArrayList<Double>();
    	for(int i = 1; i < 501; i++) {
    		int clicks = sim.runOneDay();
            if (clicks < 0) {
                i--;
                continue;
            }
    		//System.out.println("Trial " + i + " : " + clicks + " / " + sim.getNumberDisplayed());
            if (i % 10 == 1) {
                ctrs.add((double)clicks);
                ts.add((double)sim.getNumberDisplayed());
            } else {
                int l = ctrs.size()-1;
                ctrs.set(l, ctrs.get(l) + clicks);
                ts.set(l, ts.get(l) + sim.getNumberDisplayed());
            }
            if (i % 10 == 0) {
                int l = ctrs.size()-1;
                ctrs.set(l, ctrs.get(l)/ts.get(l));
                System.out.println(ctrs.get(l));
            }
    	}
    	
    	
    	// HashMap<Article, Double> b = new HashMap<Article, Double>();
    	// HashMap<Article, Double> j = new HashMap<Article, Double>();
    	// HashMap<Article, Double> c = new HashMap<Article, Double>();
    	// HashMap<Article, Double> d = new HashMap<Article, Double>();
    	
    	// Calendar dateBilly = Calendar.getInstance();
    	// dateBilly.set(2010, Calendar.MAY, 7);
    	// Calendar dateJoe = Calendar.getInstance();
    	// dateJoe.set(2010, Calendar.MAY, 7);
    	// Calendar dateDan = Calendar.getInstance();
    	// dateDan.set(2010, Calendar.MAY, 7);
    	// Calendar dateChris = Calendar.getInstance();
    	// dateChris.set(2010, Calendar.MAY, 7);
    	// Thread[] t = new Thread[4];
    	// t[0] = new Thread(new RunnableSimulator("Billy", billy, 'e', 50, 20, 0, .9, 20, s, dateBilly));
    	// t[1] = new Thread(new RunnableSimulator("Joe", joe, 'e', 50, 20, 0, .9, 50, s, dateJoe));
    	// t[2] = new Thread(new RunnableSimulator("Dan", chris, 'e', 50, 20, 0, .9, 150, s, dateDan));
    	// t[3] = new Thread(new RunnableSimulator("Chris", dan, 'e', 50, 20, 0, .9, 500, s, dateChris));
    	
    	// for (int i = 0; i < 4; i++)
    	// 	t[i].start();
    	// for (int i = 0; i < 4; i++) {
    	// 	try { t[i].join(); }
    	// 	catch(InterruptedException e) {System.out.println("Error, interrupted");}
    	// }
    	
    	
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

