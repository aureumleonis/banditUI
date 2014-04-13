package articleRecommendationSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;



public class Application {

	
	public static void main(String[] args) throws Exception {
		Application app = new Application(); 
		String[] topics = { "Entertainment", "Health", "MoneyWatch", "Politics", "Sports",
				"Tech", "US", "World" };
		for (String topic : topics)
		{
			app.FindFrequentWords(topic, 1000, 10000);
			app.FindFrequentWords(topic, 2000, 10000);
			app.FindFrequentWords(topic, 3000, 10000);
			app.FindFrequentWords(topic, 4000, 10000);
			app.FindFrequentWords(topic, 5000, 10000);
		}
	}
	
	
	@SuppressWarnings("unused")
	private void FindFrequentWords(String topic, int min_appearances, int max_appearances) throws Exception
	{
		HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
		HashMap<String, Integer> frequentCounts = new HashMap<String, Integer>();
		BufferedReader in = new BufferedReader(new FileReader("WordCounts-"+topic+".csv"));
		String inline;
		while ((inline = in.readLine())!=null)
		{
			String[] s = inline.split(",");
			wordCounts.put(s[0], Integer.parseInt(s[1]));
		}
		in.close();
		
		for (String s : wordCounts.keySet())
		{
			if (wordCounts.get(s) > min_appearances && wordCounts.get(s) < max_appearances 
					&& s.length() > 1)
			{
				try {
					Float.parseFloat(s);
				}
				catch(Exception e)
				{
					frequentCounts.put(s, wordCounts.get(s));
				}
			}
		}
		
		String outfile = "WordCounts-" + topic + "-" + min_appearances + ".csv";
		BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
		for (String s : frequentCounts.keySet())
		{
			out.write(s + ',' + frequentCounts.get(s) + "\r\n");
		}
		out.close();
	}

	@SuppressWarnings("unused")
	private void GetArticlesFromRangeOfPages(String topic, int lowPage, int highPage)
	{
		ArrayList<Thread> threads = new ArrayList<Thread>();
		int ub;
		for (int j = lowPage; j <= highPage; j+=100)
		{ 
			ub = Math.min(j+100, highPage);
			for (int i = j; i < ub; i++)
			{
				threads.add(new Thread(new CBSNewsCrawler("tech", i, "News-Tech/page" + i + ".csv")));
				System.out.println(i);
			}
			for (int i = 0; i < threads.size(); i++)
				threads.get(i).start();
			for (int i = 0; i < threads.size(); i++)
			{
				try {
					threads.get(i).join();
				} catch (InterruptedException e) {}
			}
		}
	}
	
	
	@SuppressWarnings("unused")
	private void ReadArticles(String topic, int start_page, int end_page, 
			int simultaneous_threads, String base_output_directory) throws IOException, InterruptedException
	{
		String inputLine;
		String url;
		String[] splitLine;
		String[] date;
		File f;
		Article a;
		Calendar d;
		ArrayList<Article> articles = new ArrayList<Article>();
		for (int i = start_page; i <= end_page; i++)
		{
			BufferedReader in = new BufferedReader(
					new FileReader("News-" + topic + "/page" + i + ".csv"));
			while((inputLine = in.readLine()) != null)
			{
				splitLine = inputLine.split(",");
				try
				{
					date = splitLine[1].split("-");
				}
				catch(IndexOutOfBoundsException e)
				{
					System.out.println("Error in page format on page " + i);
					continue;
				}
				
				d = Calendar.getInstance();
				try
				{
					d.set(Calendar.MONTH, Integer.parseInt(date[0])-1);
					d.set(Calendar.DATE, Integer.parseInt(date[1]));
					d.set(Calendar.YEAR, Integer.parseInt(date[2]));
				}
				catch(NumberFormatException e)
				{
					System.out.println("Error in date on page " + i);
					continue;
				}
				url = splitLine[0];
				a = new Article(d, url);
				articles.add(a);
			}
			in.close();
		}
		
		int num_articles = articles.size();
		for (int i = 0; i < num_articles; i++)
		{
			f = new File(base_output_directory + "/" + 
					(articles.get(i).date.get(Calendar.MONTH)+1) + '-' + 
					articles.get(i).date.get(Calendar.DATE) + '-' + 
					articles.get(i).date.get(Calendar.YEAR));
			f.mkdir();
		}
		
		System.out.println("Done creating article list.");
		
		int num_threads_forked = 0;
		String outfile;
		Thread t;
		ArrayList<Thread> threads = new ArrayList<Thread>();
		while ( num_threads_forked < num_articles)
		{
			for (int i = 0; i  < simultaneous_threads; i++)
			{
				a = articles.get(num_threads_forked);
				try
				{
					outfile = base_output_directory + "/" + 
						(a.date.get(Calendar.MONTH)+1) + '-' + 
						a.date.get(Calendar.DATE) + '-' + 
						a.date.get(Calendar.YEAR) + "/" +
						a.url.substring(a.url.indexOf("news/")+5, a.url.length()-1) +
						".txt";
				}
				catch(IndexOutOfBoundsException e)
				{
					outfile = base_output_directory + "/" + 
							(a.date.get(Calendar.MONTH)+1) + '-' + 
							a.date.get(Calendar.DATE) + '-' + 
							a.date.get(Calendar.YEAR) + "/" +
							a.url + ".txt";
				}
				t = new Thread(new ArticleReader(a, outfile, topic));
				threads.add(t);
				num_threads_forked++;
				if (num_threads_forked == num_articles)
				{
					break;
				}
			}
			for (Thread thread : threads)
				thread.start();
			
			for (Thread thread : threads)
				thread.join();
			System.out.println("Joined threads.");
			threads.clear();
		}
		
	}
}
