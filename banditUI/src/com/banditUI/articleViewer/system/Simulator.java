package com.banditUI.articleViewer.system;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class Simulator {
	protected Exp4Simulator bandit;
	protected Person sim;
	protected Calendar day;
	protected int trial = 0, seen = 0, clicks = 0;
	protected List<Entry<Article, Integer>> currentDisplay;

	public Simulator(Person p, int exps, ArticleSource source, Calendar start, int trials, double rate) {
		// Create a random user
		sim = p;
		// init the bandit with a random guess for sim
		bandit = new Exp4Simulator(sim, exps, source, 8, rate);
		day = start;
	}
	
	public Simulator(Person p, Expert[] exps, ArticleSource source, Calendar start, int trials, double rate) {
		// Create a random user
		sim = p;
		// init the bandit with a random guess for sim
		bandit = new Exp4Simulator(sim, exps, source, 8, rate);
		day = start;
	}

	public abstract HashMap<Article, Boolean> simulateClicks();

	private void setDisplay(HashMap<Article, Integer> display) {
		currentDisplay = new ArrayList(display.entrySet());
		Collections.sort(currentDisplay, new Comparator<Entry<Article, Integer>>() {
			public int compare(Entry<Article, Integer> o1, Entry<Article, Integer> o2) {
				return Integer.compare(o1.getValue(), o2.getValue());
			}
		});
	}

	public int runOneDay() {
		int clicksToday = 0;
		int result = bandit.runSimulationOneDay(day);
		if (result < 0 ) {
			return -1;
		}
		HashMap<Article, Integer> display = bandit.getCurrentDisplay();
		setDisplay(display);
		HashMap<Article, Boolean> clicked = simulateClicks();
		// update CTR
		trial++;
		seen += display.size();
		for (boolean click: clicked.values()) {
			if (click){ 
				clicks++;
				clicksToday++;
			}
		}
		bandit.UpdateExpertTrust(clicked);
		//day.add(Calendar.DATE, 1);
		return clicksToday;
	}

	public double getCTR() {
		return clicks/(double)seen;
	}
}