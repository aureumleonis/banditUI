package com.banditUI.articleViewer.system;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.lang.Math;

public class ExpertSimulator extends Simulator {
	
	public ExpertSimulator(int exps, ArticleSource source, Calendar start, int trials, double rate) {
		super(exps, source, start, trials, rate);
	}
	
	@Override
	public HashMap<Article, Boolean> simulateClicks() {
		HashMap<Article, Boolean> clicks = new HashMap<Article, Boolean>();
		for (Entry<Article, Integer> disp_row: currentDisplay) {
			double score = ClickProbCalc.calcClickProb(disp_row.getKey(), sim);
			for (int i = disp_row.getValue(); i > 0; i--) {
				score = sim.applyEmphasisValueFunction(score);
			}
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