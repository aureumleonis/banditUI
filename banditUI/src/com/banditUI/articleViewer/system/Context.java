package com.banditUI.articleViewer.system;

import java.util.ArrayList;
import java.util.HashMap;


public class Context {
	
    public static final int ENTERTAINMENT = 0;
    public static final int HEALTH = 1;
    public static final int MONEYWATCH = 2;
    public static final int POLITICS = 3;
    public static final int SPORTS = 4;
    public static final int TECH = 5;
    public static final int US = 6;
    public static final int WORLD = 7;

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
    // These can take on values in the range [0,1)
    // All context disposition estimates start optimistic.
    private double Entertainment_Disposition = Math.random();
    private double Health_Disposition = Math.random();
    private double MoneyWatch_Disposition = Math.random();
    private double Politics_Disposition = Math.random();
    private double Sports_Disposition = Math.random();
    private double Tech_Disposition = Math.random();
    private double US_Disposition = Math.random();
    private double World_Disposition = Math.random();

    //
    // Numbers of times each kind of article has been displayed.
    // This is used to update the context after getting feedback
    // when articles are displayed.
    private int Num_Displayed_Entertainment = 0;
    private int Num_Displayed_Health = 0;
    private int Num_Displayed_MoneyWatch = 0;
    private int Num_Displayed_Politics = 0;
    private int Num_Displayed_Sports = 0;
    private int Num_Displayed_Tech = 0;
    private int Num_Displayed_US = 0;
    private int Num_Displayed_World = 0;
    
    //
    // Feature vectors for each article topic.
    // Each vector is the corresponding DICT_SIZE in length
    // and each entry is in the range [0, 1]
    private HashMap<String, Double> Entertainment_Feature_Vector;    
    private HashMap<String, Double> Health_Feature_Vector;
    private HashMap<String, Double> MoneyWatch_Feature_Vector;
    private HashMap<String, Double> Politics_Feature_Vector;
    private HashMap<String, Double> Sports_Feature_Vector;
    private HashMap<String, Double> Tech_Feature_Vector;
    private HashMap<String, Double> US_Feature_Vector;
    private HashMap<String, Double> World_Feature_Vector;

    
    public Context() {
    	//
    	// Fill feature vectors with optimistic estimates
        fillFeatureVectors();
    }
    
    public Context(double[] disps) {
        Entertainment_Disposition = disps[0];
        Health_Disposition = disps[1];
        MoneyWatch_Disposition = disps[2];
        Politics_Disposition = disps[3];
        Sports_Disposition = disps[4];
        Tech_Disposition = disps[5];
        US_Disposition = disps[6];
        World_Disposition = disps[7];
        fillFeatureVectors();
    }
    
    private void fillFeatureVectors() {
    	Entertainment_Feature_Vector = new HashMap<String, Double>();
        Health_Feature_Vector = new HashMap<String, Double>();
        MoneyWatch_Feature_Vector = new HashMap<String, Double>();
        Politics_Feature_Vector = new HashMap<String, Double>();
        Sports_Feature_Vector = new HashMap<String, Double>(); 
        Tech_Feature_Vector = new HashMap<String, Double>();
        US_Feature_Vector = new HashMap<String, Double>();
        World_Feature_Vector = new HashMap<String, Double>();
        
        ArrayList<HashMap<String, Double>> FeatureVectors = new ArrayList<HashMap<String, Double>>();
        FeatureVectors.add(Entertainment_Feature_Vector);
        FeatureVectors.add(Health_Feature_Vector);
        FeatureVectors.add(MoneyWatch_Feature_Vector);
        FeatureVectors.add(Politics_Feature_Vector);
        FeatureVectors.add(Sports_Feature_Vector);
        FeatureVectors.add(Tech_Feature_Vector);
        FeatureVectors.add(US_Feature_Vector);
        FeatureVectors.add(World_Feature_Vector);
        
        Dictionaries dictionaries = Dictionaries.getInstance();
        String[] Topics = {"Entertainment", "Health", "MoneyWatch", "Politics",
        		"Sports", "Tech", "US", "World" };
        
        // 
        // Fill in each feature vector with random doubles in [0, 1)
        for (int i = 0; i < Topics.length; i++) {
        	HashMap<String, Double> dict = dictionaries.getDictionary(Topics[i]);
        	HashMap<String, Double> features = FeatureVectors.get(i);
        	for (String key : dict.keySet()) {
        		features.put(key, Math.random());
        	}
        }
    }
 
    public void incrementArticlesSeen(int articleTopic) {
    	switch (articleTopic) {
    	  case ENTERTAINMENT:
    		  Num_Displayed_Entertainment++;
    		  break;
    	  case HEALTH:
    		  Num_Displayed_Health++;
    		  break;
    	  case MONEYWATCH:
    		  Num_Displayed_MoneyWatch++;
    		  break;
    	  case POLITICS:
    		  Num_Displayed_Politics++;
    		  break;
    	  case SPORTS:
    		  Num_Displayed_Sports++;
    		  break;
    	  case TECH:
    		  Num_Displayed_Tech++;
    		  break;
    	  case US:
    		  Num_Displayed_US++;
    		  break;
    	  case WORLD:
    		  Num_Displayed_World++;
    		  break;
    	  default:
    		  return;
    	}
    }
    
    public HashMap<String, Double> getFeatureVector(int topic) {
    	switch (topic) {
    	case ENTERTAINMENT:
    		return Entertainment_Feature_Vector;
    	case HEALTH:
    		return Health_Feature_Vector;
    	case MONEYWATCH:
    		return MoneyWatch_Feature_Vector;
    	case POLITICS:
    		return Politics_Feature_Vector;
    	case SPORTS:
    		return Sports_Feature_Vector;
    	case TECH:
    		return Tech_Feature_Vector;
    	case US:
    		return US_Feature_Vector;
    	case WORLD:
    		return World_Feature_Vector;
    	default:
    		System.out.println("Invalid request for feature vector.");
    		return null;	
    	}
    }
    
    public HashMap<String, Double> getFeatureVector(String topic) {
    	switch (topic.toUpperCase()) {
    	case "ENTERTAINMENT":
    		return Entertainment_Feature_Vector;
    	case "HEALTH":
    		return Health_Feature_Vector;
    	case "MONEYWATCH":
    		return MoneyWatch_Feature_Vector;
    	case "POLITICS":
    		return Politics_Feature_Vector;
    	case "SPORTS":
    		return Sports_Feature_Vector;
    	case "TECH":
    		return Tech_Feature_Vector;
    	case "US":
    		return US_Feature_Vector;
    	case "WORLD":
    		return World_Feature_Vector;
    	default:
    		System.out.println("Invalid request for feature vector.");
    		return null;	
    	}
    }
    
    public double getDisposition(String topic) {
    	switch (topic.toUpperCase()) {
    	case "ENTERTAINMENT":
    		return Entertainment_Disposition;
    	case "HEALTH":
    		return Health_Disposition;
    	case "MONEYWATCH":
    		return MoneyWatch_Disposition;
    	case "POLITICS":
    		return Politics_Disposition;
    	case "SPORTS":
    		return Sports_Disposition;
    	case "TECH":
    		return Tech_Disposition;
    	case "US":
    		return US_Disposition;
    	case "WORLD":
    		return World_Disposition;
    	default:
    		System.out.println("Invalid request for disposition.");
    		return 0;	
    	}
    }
}
