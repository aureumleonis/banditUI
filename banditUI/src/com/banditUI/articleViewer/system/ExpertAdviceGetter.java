package com.banditUI.articleViewer.system;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ExpertAdviceGetter implements Runnable {
	
	private Expert e;
	private HashMap<Article, Integer> articles;
	private HashMap<Expert, HashMap<Article, Double>> expertAdvice;
	private ReentrantLock lock;
	
	public ExpertAdviceGetter(Expert expert, HashMap<Article, Integer> a, 
			HashMap<Expert, HashMap<Article,Double>> advices, ReentrantLock l) {
		e = expert;
		articles = a; 
		expertAdvice = advices;
		lock = l;
	}
	
	@Override
	public void run() {
		HashMap<Article, Double> myAdvice = e.advise(articles);
		lock.lock();
		expertAdvice.put(e, myAdvice);
		lock.unlock();
	}
}
