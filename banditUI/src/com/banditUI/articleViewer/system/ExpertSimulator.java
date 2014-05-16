package com.banditUI.articleViewer.system;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.lang.Math;

public class ExpertSimulator extends Simulator {
	
	public ExpertSimulator(Person p, int exps, ArticleSource source, Calendar start, int trials, double rate) {
		super(p, exps, source, start, trials, rate);
	}
	public ExpertSimulator(Person p, Expert[] exps, ArticleSource source, Calendar start, int trials, double rate) {
		super(p, exps, source, start, trials, rate);
	}
	
	@Override
	public HashMap<Article, Boolean> simulateClicks() {
		HashMap <Article, Boolean> clicks = new HashMap<Article, Boolean>();
		HashMap<Article, Integer> display = bandit.getCurrentDisplay();
		
		for (Article a : display.keySet()) {
			
			if (display.get(a) == 0) {
				clicks.put(a, false);
				continue;
			}
				
			// Calculate click_prob without emphasis.
			double click_prob = ClickProbCalc.calcClickProb(a, sim);
			// For each box added onto the first for display, apply
			// the Person's emphasis value function.
			for (int i = 1; i < display.get(a); i++)
				click_prob = sim.applyEmphasisValueFunction(click_prob);
			
			
			double r = Math.random();
			if (r < click_prob) {
				clicks.put(a,true);
			}
			else {
				clicks.put(a, false);
			}		
		}
		return clicks;
	}
}