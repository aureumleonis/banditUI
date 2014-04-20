package com.banditUI.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.regex.Pattern;

public class ArticleReader implements Runnable {

	private Article article;
	private String outfile;
	private String topic;
	
	public ArticleReader(Article a, String out, String top)
	{
		article = a;
		outfile = out;
		topic = top;
	}
	
	@Override
	public void run() {
		String inputLine;
		BufferedReader in;
		URL articleURL;
		try {
			articleURL = new URL(article.url);
			in = new BufferedReader(new InputStreamReader(articleURL.openStream()));
		} catch (IOException e) {
			System.out.println('1' + e.getMessage() + ": " + article.url);
			return;
		}
		
		 try {
			inputLine = in.readLine();
		} catch (IOException e) {
			System.out.println('2' + e.getMessage());
			return;
		}
		//Read until you find the start of the article
	    while (inputLine != null)
	    {
	    	inputLine = inputLine.trim();
	    	// When you find it, break
	    	if (inputLine.startsWith("<div class=\"entry\" itemprop=\"articleBody\""))
	    		break;	
	    	try {
				inputLine = in.readLine();
			} catch (IOException e) {
				System.out.println('3' + e.getMessage());
					return;
			}
	    }
	    
	    // If the article was never found, return.
	    if (inputLine == null)
	    {
			System.out.println("4 Error : " + article.url);
	    	return;
	    }
	    
	    String articleText = "";
	    // While you haven't found the end of the article, keep reading
	    while(inputLine != null)
	    {

	    	inputLine = inputLine.trim();
	    	// If you find the end, break.
	    	if (inputLine.startsWith("<div class=\"copyright\">"))
	    		break;
	    	
	    	articleText += inputLine;
	    	try {
				inputLine = in.readLine();
			} catch (IOException e) {
				System.out.println('4' + e.getMessage());
				return;
			}

	    }
	    
	    // If reading the article wasn't terminated correctly, then return.
	    if (inputLine == null)
	    {
			System.out.println("5 Error : " + article.url);
	    	return;
	    }
	    
	    articleText = PolishArticle(articleText);
	    
	    article.story = articleText;
	    
	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(
					new FileWriter(outfile));
			writer.write("DATE : " + (article.date.get(Calendar.MONTH)+1) + '-' + 
		    		article.date.get(Calendar.DATE) + '-' + 
					article.date.get(Calendar.YEAR) + 
					System.lineSeparator() + 
					System.lineSeparator() + 
					"TOPIC : " + topic +
					System.lineSeparator() + 
					System.lineSeparator() +
					"URL : " + article.url +
					System.lineSeparator() + 
					System.lineSeparator());
		    writer.write(articleText);
		    
		    writer.close();
		    System.out.println("Wrote article "+ article.url);
		} catch (IOException e) {
			System.out.println("Failed to write article");
			return;
		}
	    
	    
	}
	
	
	
	private String PolishArticle(String text)
	{
		String newArticle = text;
		
		// First, get rid of all figure captions
		newArticle = newArticle.replaceAll("<figcaption>.*</figcaption>", "");
		
		int index = 0;
        int numremoved;
        char charAtIndex;
        String toBeRemoved = "";
        
        while (index < newArticle.length())
        {

        	if ((charAtIndex = newArticle.charAt(index)) == '<')
        	{
        		numremoved = 0;
        		while (charAtIndex != '>')
        		{

        			toBeRemoved += charAtIndex;
        			index++;
        			charAtIndex = newArticle.charAt(index);
        			numremoved++;
        		}
        		toBeRemoved += '>';
        		newArticle = newArticle.replaceAll(Pattern.quote(toBeRemoved), "");
        		toBeRemoved = "";
        		index -= numremoved;
        	}
        	else
        		index++;
        }
        
        return newArticle;
	}
	
}
