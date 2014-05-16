package com.banditUI.articleViewer.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	
	public Expert(Context c) {
		context = c;
		beta = Math.random() * 0.8;
		alpha = Math.random() * beta;
	}

	public Expert(Context c, double a, double b) {
		context  = c;
		alpha = a;
		beta = b;
	}
	
	// Input articlesAvailable is a hashmap from articles to how many squares in the
	// display they are currently taking up.
	public HashMap<Article, Double> advise(HashMap<Article, Integer> articles) {
		int num_articles = articles.keySet().size();
		double click_prob, total=0;
		int num_spaces_taken;
		String topic;
		HashMap<Article, Double> articleVals = new HashMap<Article, Double>();
		for (Article a : articles.keySet()){
			topic = a.getTopic();
			click_prob = ClickProbCalc.calcClickProb(a, context);
			// Apply the emphasis value function the necessary number of times.
			num_spaces_taken = articles.get(a);
			click_prob = ApplyEmphasisValueFunction(click_prob, num_spaces_taken-1);
			
			articleVals.put(a, click_prob);

		}
		
		// Get top 8 articles 
		ArrayList<Double> allVals = new ArrayList<Double>();
		int ind = 0;
		for (Article a : articleVals.keySet()) {
			allVals.add(articleVals.get(a));
			ind++;
		}
		Collections.sort(allVals, Collections.reverseOrder());
		double lowestVal = allVals.get(Math.min(7, allVals.size()-1));
		for (Article a : articleVals.keySet()) {
			if (articleVals.get(a) < lowestVal) {
				articleVals.put(a, 0.0);
			}
			else {
				total += articleVals.get(a);
			}
		}
		
		// Finally, normalize the recommendations.
		for (Article key : articleVals.keySet()){
			articleVals.put(key, articleVals.get(key)/total);
		}

		
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
