import java.util.*;

public class Line {
	
	int number;
	LinkedList<String> line;
	int wordCount;
	int lineCount;
	int charCount;


	public Line() {
		
		this.number = 1;
		this.line = new LinkedList<String>();
		this.wordCount = 0;
		this.lineCount = 0;
	   this.charCount = 0;
		
	}
	
	public Line(int number) {
		
		this.number = number;
		this.line = new LinkedList<String>();
		this.wordCount = 0;
		this.lineCount = 0;
		this.charCount = 0;
	
	}
	
	public int getLineNumber() {
		
		return number;
		
	}
	
	public int getWordCount() {
		
		return wordCount;
	
	}
	
	public int getCharCount() {
		
		return charCount;
	
	}
	
	public int getLineCount() {
		
		return lineCount;
	
	}
	
	public void setLineNumber(int number) {
		
		this.number = number;
	
	}
	
	public void addWords(String input) {
		
		String[] words;
		char dummyChar;
		
		for (int i = 0; i <= input.length() - 1; i++) {
			dummyChar = input.charAt(i); //add matches non whitespace
			charCount++;
		}
			
		words = input.split("\\s+");
		
		for (int i = 0; i <= words.length - 1; i++) {
		
			line.addLast(words[i]);
			wordCount++;
	
		}
		
		lineCount++;
	}
	
	public int[] searchForWord(String keyWord) {
		
		LinkedList<Integer> indexOfKeyWord = new LinkedList<Integer>();
		
		Iterator<String> currentLine = line.iterator();
		int count = 1;
			
		while (currentLine.hasNext()) {
			
			String currentElem = currentLine.next();
			
			if (currentElem.equals(keyWord)) 
				indexOfKeyWord.addLast(count);
				
			count++;
		
		}
		
		int[] indexOfKeyWordsArray;
		
		if (indexOfKeyWord.empty()) {
			indexOfKeyWordsArray = new int[1];
			indexOfKeyWordsArray[0] = -1;
			return indexOfKeyWordsArray; // return -1 if not found
		}
		else {
			indexOfKeyWordsArray = new int[indexOfKeyWord.size()];
			for (int i = 0; i <= indexOfKeyWordsArray.length - 1; i++) {
				indexOfKeyWordsArray[i] = indexOfKeyWord.get(i + 1);
			}
			
			return indexOfKeyWordsArray;
		}
	
	}
	
	public void replaceWord(String word1, String word2) {
		
		int[] indexOfWord1;
		String dummyReturn;
		
		indexOfWord1 = searchForWord(word1);
		
		if (!(indexOfWord1[0] < 0)) {	
			for (int i = 0; i <= indexOfWord1.length - 1; i++) {
				dummyReturn = line.remove(indexOfWord1[i]);	
				line.add(word2, indexOfWord1[i]);
			}
		}
	
	}
	
	public String stringToFile() {
		
		String result = "";

		result = line.toString() + "\n";
		
		return result;
	}
	
	public String toString() {
		
		String result = "";

		result = this.getLineNumber() + " " + line.toString() + "\n";
		
		return result;
	}

} // end class
		