package com.banditUI.articleViewer.system;

import java.util.HashMap;

public class Expert {

	private double alpha;
	private double beta;
	private Context context;
	
	public Expert() {
		context = new Context();
		beta = Math.random() * 0.8;
		alpha = Math.random() * beta;
	}
	
	// Input articlesAvailable is a hashmap from articles to how many squares in the
	// display they are currently taking up.
	public HashMap<Article, Double> advise(HashMap<Article, Integer> articles) {
		double click_prob, total=0;
		int num_spaces_taken;
		String topic;
		HashMap<Article, Double> articleVals = new HashMap<Article, Double>();
		for (Article a : articles.keySet()){
			topic = a.getTopic();
			click_prob = ClickProbCalc.calcClickProb(a);
			// Apply the emphasis value function the necessary number of times.
			num_spaces_taken = articles.get(a);
			click_prob = ApplyEmphasisValueFunction(click_prob, num_spaces_taken);
			total += click_prob;
			
			articleVals.put(a, click_prob);
		}
		
		// Finally, normalize the recommendations.
		for (Article key : articleVals.keySet())
			articleVals.put(key, articleVals.get(key)/total);
		
		return articleVals;
	}
	
	private double ApplyEmphasisValueFunction(double input, int num_times) {
		double val = input;
		for (int i = 0; i < num_times; i++) {
			val += Math.min(alpha*val, beta*(1-val));
		}
		return val;
	}
}
