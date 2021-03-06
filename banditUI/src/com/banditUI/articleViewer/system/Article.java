package com.banditUI.articleViewer.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class Article {

	private Calendar date;
	private String url;
	private String topic;
	private String story;
	private HashMap<String, Integer> featureVector;
	
	
	public Article() {};

	public Article(String file) {
		try {
			readArticle(file);
			String fvLoc = file.replace("\\Articles\\", "\\FeatureVectors\\");
			readFeatureVector(fvLoc);
		}
		catch( IOException e ) {
			System.out.println("Unable to find file : " + file);
			date = null;
			url = "";
			topic = "";
			story = "";
		}
		
	}
	
	public Article(Calendar d, String u)
	{
		date = d;
		url = u;
	}

	private void readFeatureVector(String infile) throws IOException {
		featureVector = new HashMap<String, Integer>();
		BufferedReader in = new BufferedReader(new FileReader(infile));
		String inputLine = in.readLine();
		String[] splitLine;
		while (inputLine != null) {
			splitLine = inputLine.split(" ");
			featureVector.put(splitLine[0], Integer.parseInt(splitLine[1]));
			inputLine = in.readLine();
		}
		in.close();
	}

	public HashMap<String, Integer> getFeatureVector() {
		return featureVector;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public String getStory() {
		return story;
	}
	
	public String getTitle() {
		String title = url.replace("http://www.cbsnews.com/news/", "");
		title = title.substring(0, title.length() - 1);
		return title;
	}
	
	private void readArticle(String infile) throws IOException
	{
		int day, month, year;
		String inputLine, dateStr, thisTopic, thisUrl, thisArticle = "";
		String[] splitLine, dateArray;
		Calendar thisDate = Calendar.getInstance();
		BufferedReader in = new BufferedReader(new FileReader(infile));

		// Read date
		inputLine = in.readLine();
		try 
		{	
			splitLine = inputLine.split(" ");
			dateStr = splitLine[2];
			dateArray = dateStr.split("-");
			month = Integer.parseInt(dateArray[0]);
			day = Integer.parseInt(dateArray[1]);
			year = Integer.parseInt(dateArray[2]);
			thisDate.set(year, month, day);
		}
		catch(Exception e) 
		{
			thisDate.set(0, 0, 0);
		}
		
		// Read Topic
		in.readLine();
		inputLine = in.readLine();
		try {
			splitLine = inputLine.split(" ");
			thisTopic = splitLine[2];
		}
		catch(Exception e)
		{
			thisTopic = "";
		}
		
		// Read URL
		in.readLine();
		inputLine = in.readLine();
		try {
			splitLine = inputLine.split(" ");
			thisUrl = splitLine[2];
		}
		catch(Exception e)
		{
			thisUrl = "";
		}
		
		in.readLine();
		
		while ((inputLine = in.readLine()) != null)
		{
			thisArticle += inputLine;
		}
		
		date = thisDate;
		topic = thisTopic;
		url = thisUrl;
		story = thisArticle;
		
		in.close();
	}
}
