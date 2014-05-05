package com.banditUI.articleViewer.system;

import java.util.Calendar;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
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
    	Calendar startDate = Calendar.getInstance();
    	Calendar endDate = Calendar.getInstance();
    	startDate.set(2013, 6, 1);
    	endDate.set(2014, 0, 1);
    	Person p = new Person(false);
    	TestFunctions.calcClickProbsAllArticles(p, startDate, endDate, 
    			"F:\\BanditProject\\Articles\\", true);
    }

}

