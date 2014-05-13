package com.banditUI.articleViewer.system;

import java.util.Calendar;
import java.util.HashMap;

public abstract class Simulator {
	protected Exp4Simulator bandit;
	protected Person sim;
	protected Calendar day;
	protected int trial = 0, seen = 0, clicks = 0;

	public Simulator(int exps, ArticleSource source, Calendar start, int trials, double rate) {
		// Create a random user
		sim = new Person(true);
		// init the bandit with a random guess for sim
		bandit = new Exp4Simulator(new Person(true), exps, source, 8, rate);
		day = start;
	}

	public abstract HashMap<Article, Boolean> simulateClicks(HashMap<Article, Integer> display);

	public void runOnce() {
		bandit.runSimulationOneDay(day);
		HashMap<Article, Integer> display = bandit.getCurrentDisplay();
		HashMap<Article, Boolean> clicked = simulateClicks(display);
		// update CTR
		trial++;
		seen += display.size();
		for (boolean click: clicked.values()) {
			if (click) clicks++;
		}
		bandit.reward(display, clicked);
	}

	public double getCTR() {
		return clicks/(double)seen;
	}
}