package com.banditUI.articleViewer.system;

import java.util.HashMap;

public class UniformExpert extends Expert {
	
	
	@Override
	public HashMap<Article, Double> advise(HashMap<Article, Integer> articles) {
		HashMap<Article, Double> articleVals = new HashMap<Article, Double>();
		int num_articles = articles.keySet().size();
		for (Article a : articles.keySet()) {
			articleVals.put(a, 1.0/num_articles);
		}
		
		return articleVals;
	}
}	
