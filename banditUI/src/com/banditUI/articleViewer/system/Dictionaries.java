package com.banditUI.articleViewer.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Dictionaries {

	private static Dictionaries instance = null;
	
	private static final int DICT_SIZE_ENTERTAINMENT = 597;
    private static final int DICT_SIZE_HEALTH        = 323;
    private static final int DICT_SIZE_MONEYWATCH    = 334;
    private static final int DICT_SIZE_POLITICS      = 478;
    private static final int DICT_SIZE_SPORTS        = 420;
    private static final int DICT_SIZE_TECH          = 582;
    private static final int DICT_SIZE_US            = 510;
    private static final int DICT_SIZE_WORLD         = 435;

    private HashMap<String, Double> DICT_ENTERTAINMENT = new HashMap<String, Double>();
    private HashMap<String, Double> DICT_HEALTH = new HashMap<String, Double>();
    private HashMap<String, Double> DICT_MONEYWATCH = new HashMap<String, Double>();
    private HashMap<String, Double> DICT_POLITICS = new HashMap<String, Double>();
    private HashMap<String, Double> DICT_SPORTS = new HashMap<String, Double>();
    private HashMap<String, Double> DICT_TECH = new HashMap<String, Double>();
    private HashMap<String, Double> DICT_US = new HashMap<String, Double>();
    private HashMap<String, Double> DICT_WORLD = new HashMap<String, Double>();
    
    private HashMap<String, HashMap<String, Double>> topics_to_dictionaries;
    
    
    protected Dictionaries () {
    	String baseDir;
    	@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
    	System.out.println("Please input the location of the feature dictionaries : ");
    	while (true) {
	    	baseDir = s.next();
	    	try {
	    		ReadInDictionaries(baseDir);
	    		break;
	    	}
	    	catch (IOException e) {
	    		System.out.println("Unable to read dictionaries. Please close all dictionary files and" +
	    				" reenter the base directory. . .");
	    		continue;
	    	}
    	}
    }
    
    public static Dictionaries getInstance() {
    	if (instance == null) {
    		instance = new Dictionaries();
    	}
    	
    	return instance;
    }
    
    
    // Throws IOException if dictionaries not found or if they can't be read.
    private void ReadInDictionaries(String basedir) throws IOException {
    	// Create lists of topics and their corresponding dictionaries.
    	String[] topics = { "Entertainment", "Health", "MoneyWatch", "Politics",
    			"Sports", "Tech", "US", "World" };
    	ArrayList<HashMap<String, Double>> dictionaries = 
    			new ArrayList<HashMap<String, Double>>();
    	dictionaries.add(DICT_ENTERTAINMENT);
    	dictionaries.add(DICT_HEALTH);
    	dictionaries.add(DICT_MONEYWATCH);
    	dictionaries.add(DICT_POLITICS);
    	dictionaries.add(DICT_SPORTS);
    	dictionaries.add(DICT_TECH);
    	dictionaries.add(DICT_US);
    	dictionaries.add(DICT_WORLD);
    	
    	
    	int lineNumber;
    	double totalWordCount, thisWordCount;
    	HashMap<String, Double> current_dict;
    	String topic, inputLine;
    	String[] splitLine;
    	BufferedReader in;
    	
    	// For each topic
    	for (int i = 0; i < topics.length; i++) {
    		// Grab and clear the corresponding dictionary.
    		current_dict = dictionaries.get(i);
    		current_dict.clear();
    		topic = topics[i];
    		in = new BufferedReader(new FileReader(basedir + "Dictionary-" + topic + ".csv"));
    		lineNumber = 0;
    		totalWordCount = 0;
    		// Read each line of the dictionary file.
    		while((inputLine = in.readLine()) != null) {
    			lineNumber++;
    			// Try to split the line into the word and the number of
    			// times it appeared, and put these values in the dictionary.
    			try {
    				splitLine = inputLine.split(",");
    				thisWordCount = Double.parseDouble(splitLine[1]);
    				current_dict.put(splitLine[0], thisWordCount);
    				totalWordCount += thisWordCount;
    			}
    			// If it fails, report an error at this line in the dictionary.
    			catch (Exception e) {
    				System.out.println("Error parsing dictionary -- ");
    				System.out.println("\t" + topic + " dictionary at line " + lineNumber);
    				continue;
    			}
    		}
    		
    		// Now Each word is mapped to the number of times it appears. Normalize this
    		// by dividing by the total word count seen in the dictionary. This gives the
    		// percentage of the dictionary's compilation that was accounted for by 
    		// each particular word, which is more useful than just the number of
    		// times this word appeared. It also makes the relative sizes of the dictionaries
    		// irrelevant.
    		for (String key : current_dict.keySet()){
    			current_dict.put(key, current_dict.get(key)/totalWordCount);
    		}
    	}
    	
    	// Now add each topic to the dictionary of topics_to_dictionaries
    	topics_to_dictionaries = new HashMap<String, HashMap<String, Double>>();
    	for (int i = 0; i < topics.length; i++)
    		topics_to_dictionaries.put(topics[i], dictionaries.get(i));
    }

    public HashMap<String, Double> getDictionary(String topic){
    	return topics_to_dictionaries.get(topic);
    }
}
