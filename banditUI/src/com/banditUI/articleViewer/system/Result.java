package com.banditUI.articleViewer.system;

import java.util.regex.*;
import java.util.Random;
import java.util.ArrayList;

class Result {
	private Article article;
	private double confidence;
	public String tempTitle;

	public Result(Article article, double confidence) {
		this.article = article;
		this.confidence = confidence;
	}

	public Article getArticle() {
		return article;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double c) {
		confidence = c;
	}

	public static String loremipsum = " Lorem ipsum dolor sit amet, consectetur "
	+ "adipiscing elit. Integer mattis vel mi at ultrices. Maecenas "
	+ "lobortis massa id consequat bibendum. Nam sapien velit, tempor a "
	+ "orci non, mattis venenatis augue. Nam at accumsan lacus, convallis "
	+ "accumsan metus. Morbi mattis libero vulputate tortor mollis placerat. "
	+ "Maecenas sodales nibh lorem, quis ultricies elit cursus et. Class "
	+ "aptent taciti sociosqu ad litora torquent per conubia nostra, per "
	+ "inceptos himenaeos. Etiam id pharetra dolor, vitae varius diam. "
	+ "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur "
	+ "dui ligula, vehicula sed fermentum ut, interdum aliquam dui. Aenean "
	+ "tellus magna, malesuada sed faucibus vitae, consequat vel.";

	public static Pattern titlePattern = Pattern.compile("\\w+\\s\\w+\\s\\w+");

	public static double orderedSample(double max, double low, Random r) {
		double s = 0;
		while (s >= max || s <= low) {
			s = r.nextDouble();
		}
		return s;
	}

	public static int randomWordStart(Random r) {
		int s = r.nextInt(650);
		while (loremipsum.charAt(s) != ' ') s--;
		return s;
	}

	public static ArrayList<Result> dummyResults(int quantity) {
		ArrayList<Result> results = new ArrayList<Result>();
		Random r = new Random();
		double upper, lower, norm = 0;
		upper = 1;
		lower = orderedSample(upper, upper/2, r);
		for (int i = 0; i < quantity; i++) {
			System.out.println(String.format("Limits: %.3f, %.3f", upper, lower));
			Article na = new Article();
			Result nr = new Result(na, orderedSample(upper, lower, r));
			norm += nr.getConfidence();
			Matcher m = titlePattern.matcher(loremipsum);
			String title = null;
			while (title == null) {
				if (m.find(randomWordStart(r))) title = m.group(0);
			}
			nr.tempTitle = title;
			results.add(nr);
			upper = nr.getConfidence();
			lower = orderedSample(upper, upper*orderedSample(0.6, 0.2, r), r);
		}
		for (Result re : results) {
			re.setConfidence(re.getConfidence()/norm);
		}
		return results;
	}
}