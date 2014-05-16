package com.banditUI.articleViewer.system;

import java.util.Calendar;

public class RunnableSimulator implements Runnable {
	private Simulator sim;
	private int days_between_samples, days_per_sample, num_samples;
	String p;
	
	public RunnableSimulator(String name, Person person, char type,  int num_sample, 
			int daysPer, int between_samples, double learning_rate, int num_experts,
			ArticleSource source, Calendar startDate) {
		if (type == 's') {
			sim = new SaliencySimulator(person, num_experts,source, startDate, 0, learning_rate);
		}
		else {
			sim = new ExpertSimulator(person, num_experts,source, startDate, 0, learning_rate);
		}
		
		days_between_samples = between_samples;
		days_per_sample = daysPer;
		num_samples = num_sample;
		p = name;
	}
	
	@Override
	public void run() {
		double[] avgCTRs = new double[num_samples];
		for (int t = 0; t < num_samples; t++) {
			System.out.println(p + " is starting trial " + t);
			double total = 0;
			for (int i = 0; i < days_per_sample; i++) {
				int result = sim.runOneDay();
    			if (result < 0) {
    				i--;
    			}
    			else {
    				total += result;
    			}
			}
			total/=days_per_sample;
    		avgCTRs[t] = total;
			for (int i = 0; i < days_between_samples; i++) {
				sim.runOneDay();
			}
		}
		
		// Output results:
		int total_days_per_sample = days_per_sample + days_between_samples;
		System.out.println("Average CTRs for " + p + " : ");
		for (int t = 0; t < num_samples; t++) {
			System.out.println(p + " day " + (t*total_days_per_sample) + " - day" + 
					(t*total_days_per_sample + days_per_sample) + " : " +
					avgCTRs[t]);
		}
		
	}

	
	
}
