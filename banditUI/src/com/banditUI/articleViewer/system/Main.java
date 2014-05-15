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
    	
    	ArticleSource s = new ArticleSource("F:/BanditProject/");
    	Article[] articles = s.getArticlesForDate(date);
    	
    	double[] chris_disps = {.9, .9, .9, .9, .1, .1, .1, .1};
    	double[] dan_disps = {.1,.1,.1,.1,.9,.9,.1,.1};
    	
    	Person billy = new Person(true);
    	Person joe = new Person(true);
    	Person chris = new Person(chris_disps, .5, .5);
    	Person dan = new Person(dan_disps, .5, .5);

    	
    	
    	ExpertSimulator sim = new ExpertSimulator(dan,10, s, date, 0, 0.9);

    	for(int i = 1; i < 501; i++) {
    		int clicks = sim.runOneDay();
    		System.out.println("Trial " + i + " : " + clicks);
    	}
    	
    	/*
    	HashMap<Article, Double> b = new HashMap<Article, Double>();
    	HashMap<Article, Double> j = new HashMap<Article, Double>();
    	HashMap<Article, Double> c = new HashMap<Article, Double>();
    	HashMap<Article, Double> d = new HashMap<Article, Double>();
    	
    	for (Article a : articles) {
    		//b.put(a, ClickProbCalc.calcClickProb(a, billy));
    		//j.put(a, ClickProbCalc.calcClickProb(a, joe));
    		System.out.println(a.getTitle());
    		System.out.println(a.getTopic());
    		c.put(a, ClickProbCalc.calcClickProb(a, chris));
    		d.put(a, ClickProbCalc.calcClickProb(a, dan));
    		System.out.println();
    	}
    	
    	for (Article a : articles) {
    		//System.out.println("Billy\t" + a.getTitle() + "\t" + b.get(a));
    		//System.out.println("Joe\t" + a.getTitle() + "\t" + j.get(a));
    		System.out.println("Chris\t" + a.getTitle() + "\t" + c.get(a));
    		System.out.println("Dan\t" + a.getTitle() + "\t" + d.get(a));
    	}
    	*/
    	
    	
    	/*
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
    	//t[0] = new Thread(new RunnableSimulator("Billy", true, 'e', 13, 25, 75, .9, 10, s, dateBilly));
    	//t[1] = new Thread(new RunnableSimulator("Joe", true, 'e', 13, 25, 75, .9, 15, s, dateJoe));
    	//t[0] = new Thread(new RunnableSimulator("Dan", true, 'e', 13, 25, 25, .9, 1000, s, dateDan));
    	//t[1] = new Thread(new RunnableSimulator("Chris", false, 'e', 13, 25, 25, .9, 250, s, dateChris));
    	
    	for (int i = 0; i < 2; i++)
    		t[i].start();
    	for (int i = 0; i < 2; i++) {
    		try { t[i].join(); }
    		catch(InterruptedException e) {System.out.println("Error, interrupted");}
    	}
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

