import java.util.*;

public class FileStack {
	
	private final int MAX_FILES = 5;
	int count;
	int fileIndex;
	LinkedList<LinkedList<Line>> stack;
	String[] openFileNames;
	
	
	public FileStack() {
		
		this.stack = new LinkedList<LinkedList<Line>>();
		this.count = 0;
		this.openFileNames = new String[MAX_FILES];
		this.fileIndex = 0;
		
	}
	
	public void addFileName(String fileName) {
		
		if (!(fileIndex >= MAX_FILES)) {
			openFileNames[fileIndex] = fileName;
			fileIndex++;
		}
		else 
			System.out.println("Error, the maximum number of files has been open.\n" +
										"please save the file you are currently editing or discard edits.");
	}
	
	public String getFileName() throws NoSuchElementException {
		
		String currentFileName = "";
		
		if (openFileNames[fileIndex] == null)
			throw (new NoSuchElementException());
			
		if (fileIndex < 0)
			fileIndex = 0;
		else
			fileIndex--;
		
		currentFileName = openFileNames[fileIndex];
		openFileNames[fileIndex] = null;
		
		return currentFileName;
	
	}
	
	public LinkedList<Line> pop() throws NoSuchElementException {
		
		LinkedList<Line> result = null;
		
		if (!isEmpty())
			throw (new NoSuchElementException());
		
		result = stack.removeFirst();
		count--;
		
		return result;
	
	}
	
	public void push(LinkedList<Line> file) throws ArrayIndexOutOfBoundsException {
		
		if(!isFull()) { 
			stack.addFirst(file);
			count++;
		}
		else 
			throw (new ArrayIndexOutOfBoundsException());
	
	}
	
	public LinkedList<Line> peek() {
	
		if (!isEmpty())
			return stack.getFirst();
		else
			return null;
	}
	
	public int size() {
		
		return count;
	
	}
	
	public boolean isEmpty() {
		
		return (count == 0);
	
	}
	
	public boolean isFull() {
		
		return (count == MAX_FILES);
	
	}
}