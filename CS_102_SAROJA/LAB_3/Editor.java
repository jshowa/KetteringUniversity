import java.io.*;
import java.util.*;

public class Editor {
	
	FileStack files;
	EditorInterface userInterface;
	LinkedList<Line> currentFile;
	int currentFileLineNumber;
	
	public Editor() {
		
		this.files = new FileStack();
		this.userInterface = new EditorInterface(); 
		this.currentFile = new LinkedList<Line>();
		this.currentFileLineNumber = 0;
		options();
	
	}
	
	public void options() {
		//1. Display Menu
		//2. Ask for input
		//3. check input
		//4. perform operation
		
		//test
		userInterface.askForInput(files);
		
		try {
			if ((userInterface.getUserInput()).charAt(0) == 'c')
				createNewFile(userInterface.getUserInput());
			else if ((userInterface.getUserInput()).charAt(0) == 'e')
				editExistingFile(userInterface.getUserInput());
			else if ((userInterface.getUserInput()).matches("[\\d]+"))
				displayLineNumber(userInterface.getUserInput());
			else if ((userInterface.getUserInput()).charAt(0) == 'g')
				replaceWord(userInterface.getUserInput());
			else if ((userInterface.getUserInput()).matches("[a]{1}[' ']{1}[\\d]+"))
				addLineAtGivenN(userInterface.getUserInput());
			else if ((userInterface.getUserInput()).charAt(0) == 'a')
				addLineAtEndOfFile();
			else if ((userInterface.getUserInput()).matches("[d]{1}[' ']{1}[\\d]+"))
				deleteLine(userInterface.getUserInput());
			else if ((userInterface.getUserInput()).equals("d"))
				discardEdits();
			else if ((userInterface.getUserInput()).equals("wc"))
				wordCount();
			else if ((userInterface.getUserInput()).equals("q"))
				quit();	
			else{
			}
		}
		catch (Exception ex) {
			if (ex instanceof IOException)
				System.out.println("Error, the file asked to be read does not exist and will not be added.\n" +
				 							"Please create a new file first.");
			if (ex instanceof IndexOutOfBoundsException)
				System.out.println("Error, the line you were trying to access doesn't exist.");
		}
	}
	
	public void createNewFile(String userInput) throws IOException {
		
		LinkedList<Line> fileToEdit = new LinkedList<Line>();
		File newFile;
		PrintWriter textOutput;
		String fileName;
		int count = 1;
		
		fileName = userInput.substring(2);
		
		newFile = new File(fileName);
		
		if (!newFile.exists()) {
		
			textOutput = new PrintWriter(newFile);
			
			Line newLine = new Line(count);
				
			newLine.addWords("");
			fileToEdit.addLast(newLine);
			
			textOutput.print(fileToEdit);
			
			currentFileLineNumber = count;
			files.addFileName(fileName);
			files.push(fileToEdit);
			currentFile = fileToEdit;
			
			displayFile(); 
		}
		else 
			System.out.println("The file already exists. Please create a file with a different name.\n");

	}
		
	
	public void editExistingFile(String userInput) throws IOException {
		
		LinkedList<Line> fileToEdit = new LinkedList<Line>();
		File existingFile;
		Scanner textInput;
		String fileName;
		int count = 1;
		
		fileName = userInput.substring(2);
		
		existingFile = new File(fileName);
		
		if (existingFile.exists()) {
			
			textInput = new Scanner(existingFile);
			
			while (textInput.hasNext()) {
				
				Line newLine = new Line(count);
				
				newLine.addWords(textInput.nextLine());
				fileToEdit.addLast(newLine);
				
				count++;
			
			}
			
			count--; // count decrement due to while loop overshoot.
			currentFileLineNumber = count;
			files.addFileName(fileName);
			files.push(fileToEdit);
			currentFile = fileToEdit;
			
			displayFile();
		}
		else 
			System.out.println("The file does not exist. Please create a file or type the name\n" +
										"of an existing file.");

	}
	
	public void displayFile() {
		
		System.out.println(currentFile);
		
		options();
	
	}
	
	public void displayLineNumber(String userInput) {
		
		int lineNumber;
		Iterator<Line> iterate = currentFile.iterator();
		int index = 1;
		boolean found = false;
		
		lineNumber = Integer.parseInt(userInput);
		
		while (iterate.hasNext() && !found) {
			if ((currentFile.get(index)).getLineNumber() == lineNumber) { 
				System.out.println(currentFile.get(index));
				found = true;
			}
			else
				index++;
		}
		
		displayFile();
	}
	
	public void replaceWord(String userInput) {
	
		String[] input;
		String word1, word2;
		Iterator<Line> fileIterator = currentFile.iterator();
		Line currentLine;
		int indexOfWord;
		
		input = userInput.split("[' ']");
		
		word1 = input[1];
		word2 = input[2];
		
		while (fileIterator.hasNext()) {
			
			currentLine = fileIterator.next();
			currentLine.replaceWord(word1, word2);
			
		}
		
		displayFile();
		
	}
	
	public void addLineAtEndOfFile() {
		
		//1. split input
		Scanner userInput = new Scanner(System.in);
		Line addedLine;
		String input;
		String[] newLine;
		
		System.out.print("input: ");
		input = userInput.nextLine();
		
		//2. create line and add words
		addedLine = new Line(currentFileLineNumber + 1);
		addedLine.addWords(input);
		currentFileLineNumber++;
		
		//3. use addLast method on current file link list to add the new line
		currentFile.addLast(addedLine);
		
		//4 display file
		displayFile();
		
	}	
	
	public void deleteLine(String userInput) {
		
		//1. split user input
		Iterator<Line> iterate = currentFile.iterator();
		Line dummyOutput;
		String[] input;
		boolean found = false;
		int lineNumber;
		int index = 1;
		
		input = userInput.split("[' ']");
		
		lineNumber = Integer.parseInt(input[1]);
		
		//2. Find line in current file link list
		
		while (iterate.hasNext() && !found) { 
			if ((currentFile.get(index)).getLineNumber() == lineNumber) { 
				dummyOutput = currentFile.remove(index); //3. remove the line
				found = true;
			}
			else
				index++;
		}
		
		//4. Display the output
		
		displayFile();
	}
	
	public void wordCount() {
		
		Iterator<Line> iterate = currentFile.iterator();
		int totalWords = 0;
		int totalLines = 0;
		int totalCharacters = 0;
		
		while (iterate.hasNext()) { 
			
			Line currentLine = iterate.next();
				
			totalWords += currentLine.getWordCount();
			totalLines += currentLine.getLineCount();
			totalCharacters += currentLine.getCharCount();
		}
		
		System.out.println("Total lines: " + totalLines + " " + "Total words: " + totalWords + " " + 
										"Total characters: " + totalCharacters + "\n");
		
		displayFile();
	
	}
	
	public void addLineAtGivenN(String userInput) {
		
		Iterator<Line> iterate = currentFile.iterator();
		Scanner scanner = new Scanner(System.in);
		Line newLine;
		Line currentLine;
		String[] input;
		int lineNumber;
		
		input = userInput.split("[' ']");
		
		lineNumber = Integer.parseInt(input[1]);
		
		while (iterate.hasNext()) { 
	
			currentLine = iterate.next();
			
			if ((currentLine.getLineNumber()) == lineNumber) {
				
				newLine = new Line();
			
				System.out.print("input: ");
				newLine.addWords(scanner.nextLine());
				
				currentFile.add(newLine, (lineNumber + 1));
			
			}
		}
		
		displayFile();
	}
	
	public void discardEdits() throws Exception {
		
		LinkedList<Line> originalFile;
		Iterator<Line> iterate;
		PrintWriter output;
		String fileName;
		
		originalFile = files.pop();
		fileName = files.getFileName();
		
		output = new PrintWriter(fileName);
		iterate = originalFile.iterator();
		
		while (iterate.hasNext())
			output.print((iterate.next()).stringToFile());
		
		output.close();
	}
	
	public void quit() {
		System.exit(0);
	}
	
} // end class