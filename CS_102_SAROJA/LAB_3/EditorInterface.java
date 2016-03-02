import java.util.*;

public class EditorInterface {
	
	Scanner userInput;
	String input;

	public EditorInterface() {
		
		this.userInput = new Scanner(System.in);
		this.input = null;
	
	}
	
	public void setUserInput(String inputValue) {
		
		while(checkUserInput(inputValue) == false) {
			
			System.out.println("Error, please type a valid command");
			inputValue = userInput.nextLine();
		
		}
		
		if (checkUserInput(inputValue))
			this.input = inputValue;
	
	}
	
	public String getUserInput() {
		
		return input;
	
	}
	
	public void askForInput(FileStack files) { // need to know whether file stack is empty before
										// switching back to editor menu
		String newInput = null; 
		
		if (files.isEmpty()) {
			System.out.print(displayMenu());
		}
		
		System.out.println("Please type a command");
		
		setUserInput(userInput.nextLine());
		
	}
	
	private boolean checkUserInput(String checkInput) {
		
		boolean validInput = false;
		
		if (checkInput.matches("[c]{1}[' ']{1}[\\w]+[.]{1}txt{1}"))
			validInput = true;
		else if (checkInput.matches("[e]{1}[' ']{1}[\\w]+[.]{1}txt{1}"))
			validInput = true;
		else if (checkInput.matches("[\\d]+"))
			validInput = true;
		else if (checkInput.matches("[g]{1}[' ']{1}[\\w]+[' ']{1}[\\w]+"))
			validInput = true;
		else if (checkInput.matches("[a]{1}"))
			validInput = true;
		else if (checkInput.matches("[a]{1}[' ']{1}[\\d]+")) 
			validInput = true;
		else if (checkInput.matches("[d]{1}[' ']{1}[\\d]+")) 
			validInput = true;
		else if (checkInput.matches("[e]{1}[' ']{1}[\\d]+"))
			validInput = true;
		else if (checkInput.matches("[r]{1}[' ']{1}[\\d]+"))
			validInput = true;
		else if (checkInput.equals("wc"))
			validInput = true;
		else if (checkInput.equals("d"))
			validInput = true;
		else if (checkInput.equals("s"))
			validInput = true;
		else if (checkInput.equals("q"))
			validInput = true;
		else 
			validInput = false;
			
		return validInput;
	
	}
	
	public String displayMenu() {
		
		String menu = "";
		 
		menu = "******************************************\n" +
				 "*  Text Editor v1.0                      *\n" +
				 "******************************************\n" +
				 "*                                        *\n" +
				 "* COMMAND OPTIONS:                       *\n" +
				 "*                                        *\n" +
				 "* c [filename] - create a new file and   *\n" +
				 "*                begin editing           *\n" +
				 "* e [filename] - edit an existing file   *\n" +
				 "* q - quit                               *\n" +
				 "*                                        *\n" +
				 "******************************************\n\n";
		
		return menu;
		
	} 
}
	