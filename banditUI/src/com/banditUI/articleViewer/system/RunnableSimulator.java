package com.banditUI.articleViewer.system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class RunnableSimulator implements Runnable {
	private Simulator sim;
	private int days_between_samples, days_per_sample, num_samples;
	String p, outfile;
	
	public RunnableSimulator(String name, Person person, char type,  int num_sample, 
			int daysPer, int between_samples, double learning_rate, int num_experts,
			ArticleSource source, Calendar startDate, String outputfile) {
		if (type == 's') {
			sim = new SaliencySimulator(person, num_experts,source, startDate, 0, learning_rate);
		}
		else {
			sim = new ExpertSimulator(person, num_experts,source, startDate, 0, learning_rate);
			System.out.println("Good");
		}
		
		days_between_samples = between_samples;
		days_per_sample = daysPer;
		num_samples = num_sample;
		p = name;
		outfile = outputfile;
	}
	
	public RunnableSimulator(String name, Person person, char type,  int num_sample, 
			int daysPer, int between_samples, double learning_rate, Expert[] exps,
			ArticleSource source, Calendar startDate, String outputfile) {
		if (type == 's') {
			sim = new SaliencySimulator(person, exps,source, startDate, 0, learning_rate);
		}
		else {
			sim = new ExpertSimulator(person, exps,source, startDate, 0, learning_rate);
			System.out.println("Good");
		}
		
		days_between_samples = between_samples;
		days_per_sample = daysPer;
		num_samples = num_sample;
		p = name;
		outfile = outputfile;
	}
	
	@Override
	public void run() {
		double[] avgCTRs = new double[num_samples];
		double total = 0;
		for (int t = 0; t < num_samples; t++) {
			System.out.println(p + " is starting trial " + t);
			total = 0;
			for (int i = 0; i < days_per_sample; i++) {
				int result = sim.runOneDay();
				System.out.println(result + " -- " + sim.getNumberDisplayed());
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
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(outfile));
		} catch (IOException e) {
			System.out.println("Unable to open " + outfile);
			return;
		}
		for (int t = 0; t < num_samples; t++) {
			try {
				out.write(avgCTRs[t] + System.lineSeparator());
			} catch (IOException e) {
				System.out.println("Error writing to outfile " + outfile);
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			System.out.println("Error closing outfile " + outfile);
		}
		
	}

	
	
}
