package articleRecommendationSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
				try {
					a = readArticle(f.getAbsolutePath());
				} catch (IOException e) {
					System.out.println("Error reading article : " + f.getAbsolutePath());
					System.out.println("\t" +e.getMessage());
					continue;
				}
				if (a.topic.equals(topic))
				{
					try {
						CountWords(a.story, wordCounts);
					} catch (IOException e) {
						System.out.println("Error counting article words : " + f.getAbsolutePath());
						System.out.println("\t" + e.getMessage());
						continue;
					}
				}
				
				System.out.println("Read article : " + f.getAbsolutePath());
			}
		}
	}
	
	public void CountWords(String article, HashMap<String, Integer> wordCounts) throws IOException
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
	
	public String FormatArticleWords(String article)
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

	public Article readArticle(String infile) throws IOException
	{
		int day, month, year;
		String inputLine, dateStr, topic, url, article = "";
		String[] splitLine, dateArray;
		Calendar date = Calendar.getInstance();
		Article a = new Article();
		BufferedReader in = new BufferedReader(new FileReader(infile));
		
		// Read date
		inputLine = in.readLine();
		try 
		{	
			splitLine = inputLine.split(" ");
			dateStr = splitLine[2];
			dateArray = dateStr.split("-");
			month = Integer.parseInt(dateArray[0]);
			day = Integer.parseInt(dateArray[1]);
			year = Integer.parseInt(dateArray[2]);
			date.set(year, month, day);
		}
		catch(Exception e) 
		{
			date.set(0, 0, 0);
		}
		
		// Read Topic
		in.readLine();
		inputLine = in.readLine();
		try {
			splitLine = inputLine.split(" ");
			topic = splitLine[2];
		}
		catch(Exception e)
		{
			topic = "";
		}
		
		// Read URL
		in.readLine();
		inputLine = in.readLine();
		try {
			splitLine = inputLine.split(" ");
			url = splitLine[2];
		}
		catch(Exception e)
		{
			url = "";
		}
		
		in.readLine();
		
		while ((inputLine = in.readLine()) != null)
		{
			article += inputLine;
		}
		
		a.date = date;
		a.topic = topic;
		a.url = url;
		a.story = article;
			
		
		in.close();
		
		return a;
	}
}
