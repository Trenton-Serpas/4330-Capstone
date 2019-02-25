import java.util.List;

public class TestClass 
{
	int currIndex;
	int docLength;
	
	String getNextWord()
	{
		String retString = "";
		
		return retString;
	}
	
	List<String> getAllWords()
	{
		List<String> allWords = null;
		
		while(currIndex < docLength)
		{
			allWords.add(getNextWord());
		}
		
		return allWords;
	}
	
	String[] getTopTen()
	{
		String[] topTen = new String[10];
		List<String> allWords = getAllWords();
		
		
		
		
		return topTen;
	}
}
