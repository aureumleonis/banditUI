package com.banditUI.articleViewer.system;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.aliasi.tokenizer.EnglishStopTokenizerFactory;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.LowerCaseTokenizerFactory;
import com.aliasi.tokenizer.PorterStemmerTokenizerFactory;
import com.aliasi.tokenizer.Tokenization;
import com.aliasi.tokenizer.TokenizerFactory;


public class WordCounter {
	
	public static void CalculateAllArticleFVs(String artBaseDir, String fvBaseDir, Calendar start, Calendar end) throws IOException {
		ArticleSource source = new ArticleSource();
		Dictionaries d = Dictionaries.getInstance();
		HashMap<String, Integer> wordcounts = new HashMap<String, Integer>();
		HashMap<String, Double> dict;
		int count;
		String dir, dateAsString, fvLoc;
		File f;
		while (start.before(end)) {
			dateAsString = "" + (start.get(Calendar.MONTH) + 1) + '-' + start.get(Calendar.DATE) +
					'-' + start.get(Calendar.YEAR);
			System.out.println("Date : " + dateAsString);
			dir = fvBaseDir + dateAsString + '/';
			f = new File(dir);
			if (!f.exists()) {
				f.mkdir();
			}
			Article[] articles = source.getArticlesForDate(start);
			if (articles == null) {
				start.add(Calendar.DATE, 1);
				continue;
			}
			for (Article a : articles) {
				wordcounts.clear();
				WordCounter.CountWords(a.getStory(), wordcounts);
				fvLoc = fvBaseDir + dateAsString + '/' + a.getTitle() + ".txt";
				BufferedWriter out = new BufferedWriter(new FileWriter(fvLoc));
				dict = d.getDictionary(a.getTopic());
				for (String word : wordcounts.keySet()) {
					
					if (dict.keySet().contains(word)) {
						count = wordcounts.get(word);
						out.write(word + ' ' + count + System.lineSeparator());
					}
					
				}
				out.close();
				
			}
			start.add(Calendar.DATE, 1);
		}
	}

	public void CountWordsAllArticles(String topic, HashMap<String, Integer> wordCounts,
			Calendar startDate, Calendar endDate, String baseDir)
	{
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
				if (a.getTopic().equals(topic))
				{
					CountWords(a.getStory(), wordCounts);
				}
				
				System.out.println("Read article : " + f.getAbsolutePath());
			}
		}
	}
	
	public static void CountWords(String article, HashMap<String, Integer> wordCounts)
	{
		article = FormatArticleWords(article);
		String[] articleWords = article.split(" ");
		for (String word : articleWords) 
		{
			if (wordCounts.containsKey(word))
				wordCounts.put(word, wordCounts.get(word)+1);
			else
				wordCounts.put(word, 1);
		}
	}
	
	public static String FormatArticleWords(String article)
	{
		StringTokenizer st = new StringTokenizer(article, ":");
		String aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out .
		article = aux;
		st = new StringTokenizer(article, ".");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out [
		article = aux;
		st = new StringTokenizer(article, "[");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out [
		article = aux;
		st = new StringTokenizer(article, "]");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out '
		article = aux;
		st = new StringTokenizer(article, "'");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out ;
		article = aux;
		st = new StringTokenizer(article, ";");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out,
		article = aux;
		st = new StringTokenizer(article, ",");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out !
		article = aux;
		st = new StringTokenizer(article, "!");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out @
		article = aux;
		st = new StringTokenizer(article, "@");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out $
		article = aux;
		st = new StringTokenizer(article, "$");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out &
		article = aux;
		st = new StringTokenizer(article, "&");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out *
		article = aux;
		st = new StringTokenizer(article, "*");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out (
		article = aux;
		st = new StringTokenizer(article, "(");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out )
		article = aux;
		st = new StringTokenizer(article, ")");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out -
		article = aux;
		st = new StringTokenizer(article, "-");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out _
		article = aux;
		st = new StringTokenizer(article, "_");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out +
		article = aux;
		st = new StringTokenizer(article, "+");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		//take out =
		article = aux;
		st = new StringTokenizer(article, "=");
		aux = "";
		while (st.hasMoreTokens()){
		aux = aux + st.nextToken();
		aux = aux + " "; //so that it does not loose a space
		}
		
		TokenizerFactory factory = IndoEuropeanTokenizerFactory.INSTANCE;
		factory = new LowerCaseTokenizerFactory(factory);
		factory = new EnglishStopTokenizerFactory(factory);
		factory = new PorterStemmerTokenizerFactory(factory);
		Tokenization tk;
		tk = new Tokenization(article, factory);
		String result = "";
		String[] tokens = tk.tokens();
		for (int j = 0; j < tokens.length; j++)
		{
			result += tokens[j] + " ";
		}
		return result;
	}

}
