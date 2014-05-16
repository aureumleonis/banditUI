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

	private void showTopics(HashMap<Article, Integer> display) {
		HashMap<String, Integer> tops = new HashMap<String, Integer>();
		HashMap<String, Integer> allt = new HashMap<String, Integer>();
		for (Article a: display.keySet()) {
			String t = a.getTopic();
			if (allt.containsKey(t)) {
				allt.put(t, allt.get(t) + 1);
			}
			else {
				allt.put(t, 1);
			}
			if (display.get(a) > 0) {
				if (tops.containsKey(t)) {
					tops.put(t, tops.get(t) + 1);
				}
				else {
					tops.put(t, 1);
				}
			}
		}
		int tsz = tops.size();
		int asz = allt.size();
		System.out.print("All distr: ");
		for(String k: allt.keySet()) {
			System.out.format("%s - %.2f ", k.substring(0,2), (allt.get(k)/(double)asz));
		}
		System.out.print("\nDisp distr: ");
		for(String k: tops.keySet()) {
			System.out.format("%s - %.2f ", k.substring(0,2), (tops.get(k)/(double)tsz));
		}
		System.out.println();
	}
	
	@Override
	public HashMap<Article, Boolean> simulateClicks() {
		HashMap <Article, Boolean> clicks = new HashMap<Article, Boolean>();
		HashMap<Article, Integer> display = bandit.getCurrentDisplay();
		//showTopics(display);
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

			click_prob = 1/(1+Math.exp(-10 * click_prob + 3))-0.047;
			
			//System.out.println("Art prob: "+click_prob);
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