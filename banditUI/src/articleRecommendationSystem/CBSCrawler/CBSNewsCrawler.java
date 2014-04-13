package articleRecommendationSystem;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class CBSNewsCrawler implements Runnable {
	
	private String category;
	private int page;
	private String outfile;

	public CBSNewsCrawler(String c, int p, String file)
	{
		category = c;
		page = p;
		outfile = file;
	}
	
	@Override
	public void run() {
		int startIndex, endIndex;
		String url = "http://www.cbsnews.com/latest/" + category + "/" + page;
		URL articleURL = null;
		ArrayList<Article> pageArticles = new ArrayList<Article>();
		Article a;
		Calendar date;
		String thisArticle;
		BufferedReader in = null;
		String inputLine = "";
		try {
			articleURL = new URL(url);
		} catch (Exception e) {
			System.out.println("1Failed to read " + category + " page " + page);
			return;
		}		
        try {
			in = new BufferedReader(new InputStreamReader(articleURL.openStream()));
		} catch (IOException e) {
			System.out.println("2Failed to read " + category + " page " + page + " : " + url);
			return;
		}
        
        try {
			inputLine = in.readLine();
		} catch (IOException e) {
			System.out.println("3Failed to read " + category + " page " + page);
		}
        while (inputLine != null)
        {
        	inputLine = inputLine.trim();
        	if (inputLine.equals("<p class=\"meta\">"))
        	{
        		thisArticle = "";
        		date = Calendar.getInstance();
        		date.set(0, 0, 0);
        		
        		while (!inputLine.startsWith("<a href=\"/news/"))
        		{
	        		try {
	        			inputLine = in.readLine();
	        			if (inputLine == null)
	        				break;
	        			else
	        				inputLine = inputLine.trim();
	        		} catch (IOException e) {
	        			System.out.println("4Failed to read " + category + " page " + page);
	        			return;
	        		}
        		}
        		if (inputLine == null)
        			break;
        		if(inputLine.startsWith("<a href=\"/news/"))
        		{
        			startIndex = inputLine.indexOf('/');
        			endIndex = inputLine.indexOf('\"', startIndex);
        			thisArticle = "http://www.cbsnews.com" + 
        					inputLine.substring(startIndex, endIndex);
        		}
        		while (!inputLine.startsWith("<span class=\"date\">"))
        		{
	        		try {
	        			inputLine = in.readLine();
	        			if (inputLine == null)
	        				break;
	        			else
	        				inputLine = inputLine.trim();
	        		} catch (IOException e) {
	        			System.out.println("5Failed to read " + category + " page " + page);
	        			return;
	        		}
        		}
        		if (inputLine == null)
        			break;
        		if (inputLine.startsWith("<span class=\"date\">"))
        		{
        			getDateFromString(date, inputLine);
        		}
        		
        		a = new Article(date, thisArticle);
        		
        		pageArticles.add(a); 
        	}
        	
        	try {
    			inputLine = in.readLine();
    		} catch (IOException e) {
    			System.out.println("6Failed to read " + category + " page " + page);
    			return;
    		}
        }
        
        
        WriteArticlesToFile(pageArticles, outfile);
        try {
			in.close();
		} catch (IOException e) {}
	}




	public void getDateFromString(Calendar date, String inputLine)
	{
		int startIndex, endIndex;
		startIndex = inputLine.indexOf('>') + 1;
		endIndex = inputLine.indexOf('<', startIndex);
		inputLine = inputLine.substring(startIndex, endIndex);
		String dateStr = inputLine.substring(0, 3);
		switch(dateStr)
		{
		case "Jan":
			date.set(Calendar.MONTH, Calendar.JANUARY);
			break;
		case "Feb":
			date.set(Calendar.MONTH, Calendar.FEBRUARY);
			break;
		case "Mar":
			date.set(Calendar.MONTH, Calendar.MARCH);
			break;
		case "Apr":
			date.set(Calendar.MONTH, Calendar.APRIL);
			break;
		case "May":
			date.set(Calendar.MONTH, Calendar.MAY);
			break;
		case "Jun":
			date.set(Calendar.MONTH, Calendar.JUNE);
			break;
		case "Jul":
			date.set(Calendar.MONTH, Calendar.JULY);
			break;
		case "Aug":
			date.set(Calendar.MONTH, Calendar.AUGUST);
			break;
		case "Sep":
			date.set(Calendar.MONTH, Calendar.SEPTEMBER);
			break;
		case "Oct":
			date.set(Calendar.MONTH, Calendar.OCTOBER);
			break;
		case "Nov":
			date.set(Calendar.MONTH, Calendar.NOVEMBER);
			break;
		case "Dec":
			date.set(Calendar.MONTH, Calendar.DECEMBER);
			break;
		default:
			date.set(Calendar.MONTH, Calendar.JANUARY);
		}
		
		String day;
		try{
			day = inputLine.substring(inputLine.indexOf(' ')+1, inputLine.indexOf(','));
			date.set(Calendar.DATE, Integer.parseInt(day));
		}
		catch(Exception e) {
			date.set(Calendar.DATE, 0);
		}
		
		String year;
		int comma;
		try {
			comma = inputLine.indexOf(',');
			year = inputLine.substring(comma+2, comma+6);
			date.set(Calendar.YEAR, Integer.parseInt(year));
		}
		catch(Exception e) {
			date.set(Calendar.YEAR, 0);
		}
	}

	
	public void WriteArticlesToFile(ArrayList<Article> articles, String file)
	{
		Calendar date;
		String url;
		FileWriter out = null;
		try {
			out = new FileWriter(file, true);
		} catch (IOException e) {
			System.out.println("7Failed to write data to file : " + file);
		}
		
		for (int i = 0; i < articles.size(); i++)
		{
			url = articles.get(i).url;
			date = articles.get(i).date;
			try {
				out.write(url + ',' + (date.get(Calendar.MONTH)+1) + '-' + date.get(Calendar.DATE) +
						'-' + date.get(Calendar.YEAR) + "\r\n");
			} catch (IOException e) {}
		}
		try {
			out.close();
		} catch (IOException e) {}
		
	}
}
