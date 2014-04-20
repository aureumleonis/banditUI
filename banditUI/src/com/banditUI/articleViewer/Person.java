package com.banditUI.articleViewer;

import java.util.Scanner;


public class Person {
    
    private static final int DICT_SIZE_ENTERTAINMENT = 597;
    private static final int DICT_SIZE_HEALTH        = 323;
    private static final int DICT_SIZE_MONEYWATCH    = 334;
    private static final int DICT_SIZE_POLITICS      = 478;
    private static final int DICT_SIZE_SPORTS        = 420;
    private static final int DICT_SIZE_TECH          = 582;
    private static final int DICT_SIZE_US            = 510;
    private static final int DICT_SIZE_WORLD         = 435;

    //
    // General dispositions for each possible article topic.
    // These can take on values in the range [0,1]
    private static double Entertainment_Disposition = 1;
    private static double Health_Disposition = 1;
    private static double MoneyWatch_Disposition = 1;
    private static double Politics_Disposition = 1;
    private static double Sports_Disposition = 1;
    private static double Tech_Disposition = 1;
    private static double US_Disposition = 1;
    private static double World_Disposition = 1;

    //
    // Feature vectors for each article topic.
    // Each vector is the corresponding DICT_SIZE in length
    // and each entry is in the range [0, 1]
    private double[] Entertainment_Feature_Vector;
    private double[] Health_Feature_Vector;
    private double[] MoneyWatch_Feature_Vector;
    private double[] Politics_Feature_Vector;
    private double[] Sports_Feature_Vector;
    private double[] Tech_Feature_Vector;
    private double[] US_Feature_Vector;
    private double[] World_Feature_Vector;

    
    public Person(boolean random) {
    	// 
    	// Either read in dispositions or generate them randomly.
    	if (random) { 
    		RandomizeDispositions(); 
    	}
    	else { 
    		ReadDispositions(); 
    	}
    	
    	//
    	// Randomly fill in feature vectors.
        FillFeatureVectors();
    }   
    
    private void FillFeatureVectors() {
    	Entertainment_Feature_Vector = new double[DICT_SIZE_ENTERTAINMENT];
        Health_Feature_Vector = new double[DICT_SIZE_HEALTH];
        MoneyWatch_Feature_Vector = new double[DICT_SIZE_MONEYWATCH];
        Politics_Feature_Vector = new double[DICT_SIZE_POLITICS]; 
        Sports_Feature_Vector = new double[DICT_SIZE_SPORTS]; 
        Tech_Feature_Vector = new double[DICT_SIZE_TECH];
        US_Feature_Vector = new double[DICT_SIZE_US];
        World_Feature_Vector = new double[DICT_SIZE_WORLD];
        
        double[][] FeatureVectors = { Entertainment_Feature_Vector,
        							  Health_Feature_Vector,
        							  MoneyWatch_Feature_Vector,
        							  Politics_Feature_Vector,
        							  Sports_Feature_Vector,
        							  Tech_Feature_Vector,
        							  US_Feature_Vector,
        							  World_Feature_Vector };

        // 
        // Fill in each feature vector with random doubles in [0, 1)
        for (int i = 0; i < FeatureVectors.length; i++) {
        	for (int j = 0; j < FeatureVectors[i].length; j++) {
        		FeatureVectors[i][j] = Math.random();
        	}
        }
        
        
    }
 
    private void RandomizeDispositions() {
    	//
    	// Fill each Disposition with a random double in [0, 1)
    	Entertainment_Disposition = Math.random();
        Health_Disposition = Math.random();
        MoneyWatch_Disposition = Math.random();
        Politics_Disposition = Math.random();
        Sports_Disposition = Math.random();
        Tech_Disposition = Math.random();
        US_Disposition = Math.random();
        World_Disposition = Math.random();
    }

    private void ReadDispositions() {
    	Scanner s = new Scanner(System.in);
    	System.out.println("Enter prior dispositions in the range [0, 1) for :");
    	
		while (Entertainment_Disposition >= 1) {
			System.out.print("Entertainment : ");
			Entertainment_Disposition = s.nextDouble();
		}
		while (Health_Disposition >= 1) {
			System.out.print("Health : ");
			Health_Disposition = s.nextDouble();
		}
		while (MoneyWatch_Disposition >= 1) {
			System.out.print("MoneyWatch : ");
			MoneyWatch_Disposition = s.nextDouble();
		}
		while (Politics_Disposition >= 1) {
			System.out.print("Politics : ");
			Politics_Disposition = s.nextDouble();
		}
		while (Sports_Disposition  >= 1) {
			System.out.print("Sports : ");
			Sports_Disposition = s.nextDouble();
		}
		while (Tech_Disposition  >= 1) {
			System.out.print("Tech : ");
			Tech_Disposition = s.nextDouble();
		}
		while (US_Disposition  >= 1) {
			System.out.print("US : ");
			US_Disposition = s.nextDouble();
		}
		while (World_Disposition  >= 1) {
			System.out.print("World : ");
			World_Disposition = s.nextDouble();
		}
    	s.close();
    }
}
