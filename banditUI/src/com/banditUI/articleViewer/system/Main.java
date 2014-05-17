package com.banditUI.articleViewer.system;

import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) throws IOException {  
    	
    	
    	// date -- Jan 201
    	Calendar date1 = Calendar.getInstance();
    	date1.set(2010, Calendar.JANUARY, 1);
    	Calendar date2 = Calendar.getInstance();
    	date2.set(2010, Calendar.JANUARY, 1);
    	Calendar date3 = Calendar.getInstance();
    	date3.set(2010, Calendar.JANUARY, 1);
    	Calendar date4 = Calendar.getInstance();
    	date4.set(2010, Calendar.JANUARY, 1);
    	Calendar date5 = Calendar.getInstance();
    	date5.set(2010, Calendar.JANUARY, 1);
    	Calendar date6 = Calendar.getInstance();
    	date6.set(2010, Calendar.JANUARY, 1);
    	Calendar date7 = Calendar.getInstance();
    	date7.set(2010, Calendar.JANUARY, 1);
    	Calendar date8 = Calendar.getInstance();
    	date8.set(2010, Calendar.JANUARY, 1);
    	Calendar date9 = Calendar.getInstance();
    	date9.set(2010, Calendar.JANUARY, 1);
    	

    	ArticleSource s = new ArticleSource("F:/BanditProject/");
    	
    	double[] chris_disps = {.9, .9, .9, .9, .1, .1, .1, .1};
    	double[] dan_disps = {.05,.05,.05,.05,.9,.9,.3,.5};

    	Person dan = new Person(dan_disps, 0.25, 0.8);
    	Thread[] t = new Thread[6];
    	t[0] = new Thread(new RunnableSimulator("Test1", dan, 'e', 50, 20, 0, .25, 200, s, date1, "dan_test1_2.txt"));
    	t[1] = new Thread(new RunnableSimulator("T2", dan, 'e', 50, 20, 0, .25, 200, s, date2, "dan_test2_2.txt"));
    	t[2] = new Thread(new RunnableSimulator("T3", dan, 'e', 50, 20, 0, .25, 200, s, date3, "dan_test3_2.txt"));
    	t[3] = new Thread(new RunnableSimulator("T4", dan, 'e', 50, 20, 0, .25, 200, s, date4, "dan_test4_2.txt"));
    	t[4] = new Thread(new RunnableSimulator("T4", dan, 'e', 50, 20, 0, .25, 200, s, date5, "dan_test5_2.txt"));
    	t[5] = new Thread(new RunnableSimulator("T5", dan, 'e', 50, 20, 0, .25, 200, s, date6, "dan_test6_2.txt"));


    	
    	for (int i = 0; i < 6; i++)
    		t[i].start();
    	for (int i = 0; i < 6; i++) {
    		try { t[i].join(); }
    		catch(InterruptedException e) {System.out.println("Error, interrupted");}
    	}
    	
    	
    	
    	/*
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
    	*/

    	

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

