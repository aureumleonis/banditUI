package com.banditUI.articleViewer.system;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class ArticleSource {

	private String rootDir;
	
	public ArticleSource() {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter root directory for article source.");
		
		while (true) {
			rootDir = s.nextLine();
    		File f = new File(rootDir);
    		if (f.exists()) {
    			break;
    		}

    		else{
	    		System.out.println("Unable to read article source. Please " +
	    				" reenter the base directory. . .");
	    		continue;
	    	}
    	}
	}
	
	public Article[] getArticlesForDate(Calendar date) {
		String dirStr = rootDir + (date.get(Calendar.MONTH)+1) + '-' + date.get(Calendar.DATE)
				+ '-' + date.get(Calendar.YEAR);
		File dir = new File(dirStr);
		File[] filesInDir = dir.listFiles();
		int num_articles = filesInDir.length;
		Article[] articles = new Article[num_articles];
		int index = 0;
		for (File f : filesInDir)
		{
			Article a = new Article(f.getAbsolutePath());
			articles[index] = a;
			index++;
		}
		
		return articles;
	}
	
}
