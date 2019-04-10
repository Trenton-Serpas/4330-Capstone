import java.util.*;

public class Parser 
{
	int currIndex = 0;
	public Scanner sc;
	Map<String, Integer> wordMap = null;
	
	class Token
	{
		int count;
		String word;
		
		Token(int c, String s)
		{
			count = c;
			word = s;
		}
	}
	
	Parser(Scanner newScanner)
	{
		sc = newScanner;
	}
	
	Parser(){};
	
	public void getAllWords()
	{
		Map<String, Integer> allWords = new TreeMap<String, Integer>();
		while(sc.hasNext())
		{
			String next = sc.next().toLowerCase();
			
			if(!allWords.containsKey(next))
				allWords.put(next, 1);
			
			else
				allWords.put(next, allWords.get(next) + 1);
		}
		
		wordMap = allWords;
	}
	
	public String[] getTopTen()
	{
		String[] topTen = new String[10];
		getAllWords();
		
		PriorityQueue<Token> pq = new PriorityQueue<Token>(wordMap.size(), new TokenComparator());
		
		for(String word : wordMap.keySet())
			pq.add(new Token(wordMap.get(word), word));
		
		for(int i = 0; i < 10; i++)
		{
			Token t = pq.poll();
			if(t == null) {}
			else			
				System.out.println(t.word);
		}
			
		return topTen;
	}
	
	class TokenComparator implements Comparator<Token>
	{
		public int compare(Token t1, Token t2)
		{
			if(t1.count < t2.count)
				return 1;
			else if(t1.count > t2.count)
				return -1;
			return 0;
		}
	}
}
