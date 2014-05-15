package com.banditUI.articleViewer.system;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.lang.Math;

public class SaliencySimulator extends Simulator {
	private double mu, sigm;

	public SaliencySimulator(boolean random, int exps, ArticleSource source, Calendar start, int trials, double rate) {
		super(random, exps, source, start, trials, rate);
	}

	private double saliency(double x) {
		return (x-mu)/sigm;
	}
	
	public HashMap<Article, Boolean> simulateClicks() {
		// Pre-compute saliency params
		double tsquares = 0, sqsquares = 0;
		for (Entry<Article, Integer> disp_row: currentDisplay) {
			tsquares += disp_row.getValue();
		}
		mu = tsquares/currentDisplay.size();
		for (Entry<Article, Integer> disp_row: currentDisplay) {
			sqsquares += Math.pow(disp_row.getValue() - mu, 2);
		}
		sigm = Math.sqrt(sqsquares/(currentDisplay.size()-1));
		// Compute the article scores
		HashMap<Article, Boolean> clicks = new HashMap<Article, Boolean>();
		HashMap<Article, Double> scores = new HashMap<Article, Double>();
		double score_total = 0;
		for (Entry<Article, Integer> disp_row: currentDisplay) {
			double score = ClickProbCalc.calcClickProb(disp_row.getKey(), sim);
			for (int i = disp_row.getValue(); i > 0; i--) {
				score = sim.applyEmphasisValueFunction(score);
			}
			score *= saliency((double)disp_row.getValue());
			score_total += score;
			scores.put(disp_row.getKey(), score);
		}
		// Normalize scores and sample
		for (Entry<Article, Integer> disp_row: currentDisplay) {
			double score = scores.get(disp_row.getKey()) / score_total;
			if (Math.random() < score) {
				clicks.put(disp_row.getKey(), true);
			}
			else {
				clicks.put(disp_row.getKey(), false);
			}
		}
		return clicks;
	}
}