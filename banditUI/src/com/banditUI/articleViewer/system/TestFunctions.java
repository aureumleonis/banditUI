package com.banditUI.articleViewer.system;

import java.io.File;
import java.util.Calendar;

public class TestFunctions {


	// This function is used to read in many files and calculate their feature vector
	// scores using the CalcFeatureVectorScore function of the ClickProbabilityCalculator
	// class. This will be used to determine the general range of scores that this function
	// returns. 
	
	public static double[] calcClickProbsAllArticles(Person p, Calendar startDate, 
			Calendar endDate, String baseDir, boolean printAll) {
		// stats indices are
		//   0 -- min value
		//   1 -- max value
		//   2 -- mean value
		//   3 -- num zero scores
		double[] stats = new double[4];
		double score;
		double min = 100;
		double max = 0;
		double mean = 0;
		double zeros = 0;
		double samples = 0;
		
		
		String dirStr;
		File dir;
		File[] filesInDir;
		Article a;
		while (startDate.before(endDate))
		{
			dirStr = baseDir + (startDate.get(Calendar.MONTH)+1) + '-' + startDate.get(Calendar.DATE)
					+ '-' + startDate.get(Calendar.YEAR);
			dir = new File(dirStr);
			filesInDir = dir.listFiles();
			for (File f : filesInDir)
			{
				a = new Article(f.getAbsolutePath());
				score = ClickProbCalc.calcClickProb(f.getAbsolutePath(), 
						p.GetFeatureVector(a.getTopic()));
				if (score < 0) continue;
				if (score < min) min = score;
				if (score > max) max = score;
				if (score == 0) zeros++;
				samples++;
				mean += score;
				if (printAll)
					System.out.println(score);
			}
			startDate.add(Calendar.DATE, 1);
		}
		mean /= samples;
		
		stats[0] = min;
		stats[1] = max;
		stats[2] = mean;
		stats[3] = zeros;
		
		System.out.println();
		System.out.println("MIN  : " + min);
		System.out.println("MAX  : " + max);
		System.out.println("MEAN : " + mean);
		System.out.println("ZERO : " + zeros);
		
		return stats;
	}
	
}
