package articleRecommendationSystem;

import java.util.Calendar;

public class Article {

	public Calendar date;
	public String url;
	public String topic;
	public String story;
	
	
	public Article() {};

	
	public Article(Calendar d, String u)
	{
		date = d;
		url = u;
	}
	
}
