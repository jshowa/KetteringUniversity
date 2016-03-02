   import java.util.*;
   import java.io.*;

    public class LibraryClientManager {
       public static void main(String[] args) throws Exception {
         String response = ""; // string used for console input
         ArrayList<Book> bookList = new ArrayList<Book>(); // array list used to hold book objects
         ArrayList<User> userList = new ArrayList<User>(); // array list used to hold user objects
         ArrayList<CheckOutRecord> checkOutRecordList = new ArrayList<CheckOutRecord>(); // array list used to hold check out record objects
      
         System.out.println("Welcome to Library Manager v1.0!\n\n" + // print title
            		 "by: Jacob Howarth and Calvin Diep\n" +
            		 "12/15/2007, Winter 2007 Term\n" +
            		 "CS 101-01, Prof. Changhua Wu\n");
      
         //1. Load backup files
         System.out.println("Loading backup files.....\n");
      	
         bookList = loadBookFile(); // method that loads book file
         userList = loadUserFile(); // method that loads user file
         checkOutRecordList = loadCheckOutRecordFile(); // method that loads check out records file
      
         while (true && !(response.equalsIgnoreCase("x"))) { // reloop program until exit
         
            response = runInterface(); // print command list 
         
            switch (response.charAt(0)) {
               case 'e': userList = addNewUser(userList);
                  break;
               case 'a': bookList = addNewBooks(bookList);
                  break;
               case 's': saveInfo(bookList, checkOutRecordList, userList);
                  break;
               case 'x': saveInfo(bookList, checkOutRecordList, userList);
                  break;
               case 'q': checkBookAvail(bookList);
                  break;
               case 'c': checkOutBook(bookList, userList, checkOutRecordList);
                  break;
               case 'k': userBooksBorrowed(bookList, userList, checkOutRecordList);
                  break;
               case 'l': findBooksPastDue(userList, checkOutRecordList);
						break;
            }
         
         }
      } // end main
   
       public static String runInterface() { // method that prints command list and returns command typed
         Scanner commandInput = new Scanner(System.in);
         String response = "";
         boolean error = true;
      
         System.out.print("*******LIBRARY MANAGEMENT COMMAND LIST*******\n" +
            		 "*\n");
         System.out.println("* e: enter new user information\n" +
            	  "* c: check out books\n" +
            	  "* s: save all information to backup files\n" +
            	  "* q: check the availability of books given a book bar code\n" +
            	  "* l: list the name, and email address of students who have books past due\n" +
            	  "* k: given the ID of user, list the books borrowed\n" +
            	  "* a: add new books into library or add copies to specific book\n" +
            	  "* x: exit Library Manager\n" + "*\n");
      
         System.out.print("Enter a command: "); // type command
         response = commandInput.nextLine();
      
         response = validateResponse(response); // error validation method for incorrect input
      
         return response;
      } // end runInterface
   
       public static String validateResponse(String response) {
         boolean error = true;
         Scanner commandInput = new Scanner(System.in);
      
         while (error) { // reloops until no errors specified by the if statements
            if (response.equalsIgnoreCase("e") || response.equalsIgnoreCase("c") || response.equalsIgnoreCase("s")
            || response.equalsIgnoreCase("q") || response.equalsIgnoreCase("l") || response.equalsIgnoreCase("k")
            || response.equalsIgnoreCase("a") || response.equalsIgnoreCase("x")) {
               error = !error; // if the input equals these letters, turn off boolean flag for loop 
            } // end if
            else if (response.equalsIgnoreCase("Help")) { // if help is typed, the command list will show up again
               commandHelp(); // method reprints command list
               System.out.print("Enter a command: ");
               response = commandInput.nextLine();
            } // end else if
            else { // if none of the above if statements are true, print error message and reloop
               System.out.println("Invalid command entered. Please enter a command from the command list or type HELP to see available commands.");
               System.out.print("Enter a command: ");
               response = commandInput.nextLine();
            } // end else
         } // end while
      
         return response;
      } // end validateResponse
   
       public static void commandHelp() {
         System.out.print("*******LIBRARY MANAGEMENT COMMAND LIST*******\n" +
            		 "*\n");
         System.out.println("* e: enter new user information\n" +
            	  "* c: check out books\n" +
            	  "* s: save all information to backup files\n" +
            	  "* q: check the availability of books given a book bar code\n" +
            	  "* l: list the name, and email address of students who have books past due\n" +
            	  "* k: given the ID of user, list the books borrowed\n" +
            	  "* a: add new books into library\n" +
            	  "* x: exit Library Manager\n" + "*\n");
      } // end commandHelp
   	
       public static ArrayList<Book> loadBookFile() throws Exception {
         ArrayList<Book> bookList = new ArrayList<Book>();
         String[] list;
      
         File bookFile = new File("librarybooks.txt"); // file object for library books to load from file
      
         if (!bookFile.exists()) {
            System.out.println("Error: File does not exist, exiting program.");
            System.exit(0);
         }
      
         Scanner readBookFile = new Scanner(bookFile);
      
         while (readBookFile.hasNext()) {
            String temp = readBookFile.nextLine(); // read in a whole line
            String delims = "[,]"; // split the string by this reg expression delimiter
            list = temp.split(delims);
            for (int i = 0; i <= list.length - 1; i++)
               list[i] = list[i].trim(); // trim excess whitespace
            bookList.add(new Book(list)); // create book object
         }
      	
         readBookFile.close(); // close scanner and return book array list
      	
         return bookList;
      } // end loadBookFile
   	
       public static ArrayList<User> loadUserFile() throws Exception {
         ArrayList<User> userList = new ArrayList<User>();
         String[] list;
      
         File userFile = new File("userinfo.txt");
      
         if (!userFile.exists()) {
            System.out.println("Error: File does not exist, exiting program.");
            System.exit(0);
         }
      
         Scanner readUserFile = new Scanner(userFile);
      
         while (readUserFile.hasNext()) {
            String temp = readUserFile.nextLine();
            String delims = "[,]";
            list = temp.split(delims);
         
            for (int i = 0; i <= list.length - 1; i++)
               list[i] = list[i].trim();
         
            switch (list[0].charAt(0)) {
               case '0': userList.add(new Student(list));
                  break;
               case '1': userList.add(new Faculty(list));
                  break;
               default: System.out.println("Unknown Data, there is no mark (0) for Student data or (1) for Faculty data in the file, exiting program.");
                  System.exit(0);
                  break;
            } // end switch
         
         } // end while
      	
         readUserFile.close();
      	
         return userList;
      } // end loadUserFile
   	
       public static ArrayList<CheckOutRecord> loadCheckOutRecordFile() throws Exception {
         ArrayList<CheckOutRecord> checkOutRecordList = new ArrayList<CheckOutRecord>();
         String[] list;
      
         File checkOutRecordFile = new File("checkoutrecords.txt");
      
         if (!checkOutRecordFile.exists()) {
            System.out.println("Error: File does not exist, exiting program.");
            System.exit(0);
         }
      
         Scanner readCheckOutRecordFile = new Scanner(checkOutRecordFile);
      
         while (readCheckOutRecordFile.hasNext()) {
            String temp = readCheckOutRecordFile.nextLine();
            String delims = "[,]";
            list = temp.split(delims);
            for (int i = 0; i <= list.length - 1; i++)
               list[i] = list[i].trim();
            checkOutRecordList.add(new CheckOutRecord(list));
         }
      	
         readCheckOutRecordFile.close();
      	
         return checkOutRecordList;
      } // end loadCheckOutRecordFile
   	
       public static ArrayList<User> addNewUser(ArrayList<User> userList) {
         String response = "";
         String firstName = "";
         String lastName = "";
         String ID = "";
         String phoneNumber = "";
         String emailAddress = "";
         String dorm_officeNumber = "";
      
         Scanner dataInput = new Scanner(System.in);
      	
         System.out.println("Directions: Please enter (0) to add Student information or a (1) to add Faculty information.");
         System.out.print("Enter a (1) or a (0): ");
         response = dataInput.next();
      
         while (!(response.equalsIgnoreCase("1") || response.equalsIgnoreCase("0"))) {
            System.out.println("Directions: Please enter (0) to add Student information or a (1) to add Faculty information.");
            System.out.print("Enter a (1) or a (0): ");
            response = dataInput.next();   
         } // end while
      
         if (response.equals("0") || response.equals("1")) {
            
            Scanner userInput = new Scanner(System.in);
         	
            System.out.print("Enter users first name: ");
            firstName = userInput.nextLine();
         
            while (!(firstName.matches("[a-zA-Z]+"))) {
               System.out.println("Invalid first name. Please enter the users first name with letters.");
               System.out.print("Enter users first name: ");
               firstName = userInput.nextLine();
            } // end while
         
            System.out.print("Enter users last name: ");
            lastName = userInput.nextLine();
         
            while (!(lastName.matches("[a-zA-Z]+"))) {
               System.out.println("Invalid last name. Please enter the users last name with letters.");
               System.out.print("Enter users last name: ");
               lastName = userInput.nextLine();
            } // end while
         
            System.out.print("Enter users identification number: ");
            ID = userInput.nextLine();
         
            while (!(ID.matches("[\\d]{5}"))) {
               System.out.println("Invalid ID entry. Please enter only 5 numbers for the users identification number.");
               System.out.print("Enter users identification number: ");
               ID = userInput.nextLine();
            } // end while
         
            System.out.print("Enter users phone number in this format (xxx-xxx-xxxx): ");
            phoneNumber = userInput.nextLine();
         
            while (!(phoneNumber.matches("[1-9][\\d]{2}-[\\d]{3}-[\\d]{4}"))) {
               System.out.println("The phone number was not entered in xxx-xxx-xxxx format, please re-enter the users phone number.");
               System.out.print("Enter users phone number in this format (xxx-xxx-xxxx): ");
               phoneNumber = userInput.nextLine();
            } // end while
         
            System.out.print("Enter the users email address: ");
            emailAddress = userInput.nextLine();
         
            while (!(emailAddress.matches("[^\\s]+[\\w[\\d[\\W]]]+[\\W]*[@][\\w[\\d]]+[\\W]*[.][a-zA-Z]+"))) {
               System.out.println("The email address entered is invalid, please re-enter the users email address.");
               System.out.print("Enter the users email address: ");
               emailAddress = userInput.nextLine();
            } // end while
         
            System.out.print("Enter the users dormitory or office number: ");
            dorm_officeNumber = userInput.nextLine();
         
            while (!(dorm_officeNumber.matches("[\\d]+"))) {
               System.out.println("Please enter numbers for the users office or dormitory number.");
               System.out.print("Enter users dormitory or office number: ");
               dorm_officeNumber = userInput.nextLine();
            } // end while
         } // end if
      
         if (response.equalsIgnoreCase("0")) {
            userList.add(new Student(response, firstName, lastName, ID, phoneNumber, emailAddress, dorm_officeNumber));
            System.out.println();
            System.out.println("Done!");
            System.out.println();        
            return userList;
         }
         else {
            userList.add(new Faculty(response, firstName, lastName, ID, phoneNumber, emailAddress, dorm_officeNumber));
            System.out.println();
            System.out.println("Done!");
            System.out.println();        
            return userList;
         }
      	
      } // end addNewUser
   	
       public static ArrayList<Book> addNewBooks(ArrayList<Book> bookList) {
         String bookTitle = "";
         String barCode = "";
         String author = "";
         String price = "";
         String copies = "";
         String response = "";
         ArrayList<Integer> searchResults = new ArrayList<Integer>();
         ArrayList<String>  toStringResults = new ArrayList<String>();
         ArrayList<Integer> resultIndexes = new ArrayList<Integer>();
         int results = 0;
         String rangeCheck = "";
      	
         Scanner responseInput = new Scanner(System.in);
         System.out.println("Would you like to add copies to a book on file or add a new book?");
         System.out.print("Please type (n) for new book or (u) to add copies to a book on file: ");
         response = responseInput.nextLine();
      	
         while (!(response.matches("n{1}") || response.matches("u{1}"))) {
            System.out.println("Invalid entry, please type (n) for a new book addition or (u) to add copies to a book on file.");
            System.out.print("Please type (n) for new book or (u) to add copies to a book on file: ");
            response = responseInput.nextLine();
         }
      	
         if (response.equalsIgnoreCase("n")) {
            Scanner titleInput = new Scanner(System.in);
            Scanner barCodeInput = new Scanner(System.in);
            Scanner authorInput = new Scanner(System.in);
            Scanner priceInput = new Scanner(System.in);
            Scanner copiesInput = new Scanner(System.in);
         
            System.out.print("Please enter the title of the book: ");
            bookTitle = titleInput.nextLine();
         
            System.out.print("Please enter the books barcode: ");
            barCode = barCodeInput.nextLine();
         
            while (!(barCode.matches("[\\d]{5}"))) {
               System.out.println("Invalid barcode entry, please enter an 5 integer number for the barcode."); 
               System.out.print("Please enter the books barcode: ");
               barCode = barCodeInput.nextLine();
            }
         
            System.out.print("Please enter the books author: ");
            author = authorInput.nextLine();
         
            while (!(author.matches("[^\\d]+"))) {
               System.out.println("Invalid author name entry, please enter letters for the author name.");
               System.out.print("Please enter the books author: ");
               author = authorInput.nextLine();
            }
         
            System.out.print("Please enter the book price: ");
            price = priceInput.nextLine();
         
            while (!(price.matches("[\\d]+[\\W]*[.][\\d]{2}"))) {
               System.out.println("Invalid price entry, please enter the dollar and cent amount for the book price.");
               System.out.print("Please enter the book price: ");
               price = priceInput.nextLine();
            }
         
            System.out.print("Please enter the number of copies: ");
            copies = copiesInput.nextLine();
         
            while (!(copies.matches("[\\d]+"))) {
               System.out.println("Invalid copy number entry, please enter an integer amount for the number of copies.");
               System.out.print("Please enter the number of copies: ");
               copies = copiesInput.nextLine();
            }
         
            bookList.add(new Book(bookTitle, barCode, author, price, copies));
            return bookList;
         
         } // end if
         else {
            response = "yes";
            Scanner redo = new Scanner(System.in);
         	
            while (true && response.equalsIgnoreCase("yes")) {
               Object[] searchList;          
               searchList = bookList.toArray();
               searchResults = searchForBook(searchList, response);
            	
               for (int i = 0; i <= searchResults.size() - 1; i++) {
               
                  results = (searchResults.get(i)).intValue();
               
                  if (results >= 0) {
                  
                     toStringResults.add((bookList.get(results)).toString());
                     resultIndexes.add(new Integer(results));
                  
                  } // end if
               
               } // end for
            	
               System.out.println();
               System.out.println((toStringResults.size()) + " returned.");
               System.out.println();
            	 
               if (toStringResults.size() == 0) {
                  System.out.println();
                  System.out.println("The book was not found, would you like to search again?");
                  System.out.print("Type (yes) to search again or (no) to discontinue searching: ");
                  response = redo.next();
               } // end if
               else {
                  for (int j = 0; j <= toStringResults.size() - 1; j++)
                     System.out.println(toStringResults.get(j));
               	
                  System.out.println("What book you like to add copies to?");
                  System.out.println("Type 0, 1, 2, ..., n to choose a book with 0 corresponding to the first book in the list above and so on)");
                  System.out.print("Enter the number here: ");            
                  response = redo.next();
                  rangeCheck += toStringResults.size();
               
                  while (response.equals(rangeCheck)) {
                     System.out.println("Invalid choice, please enter a number in accordance to the instructions, ");
                     System.out.println("for example if the search returns 1 book, please type 0 and nothing higher.");
                     System.out.println("What book you like to add copies to? ");
                     System.out.println("Type 0, 1, 2, ..., n to choose a book with 0 corresponding to the first book in the list above and so on): ");
                     System.out.print("Enter the number here: ");              
                     response = redo.next();
                  }
               
                  while (!(response.matches("[\\d]+"))) {
                     System.out.println("Invalid input, please type 0, 1, 2, ..., n in the order of the search results" + "\n" + "to choose which book to add copies to.");
                     System.out.print("Please enter 0, 1, 2, ..., n in the order of the search results to choose a book to add copies to: ");
                     response = redo.next();
                  }
               	
                  results = Integer.parseInt(response);
                  results = (resultIndexes.get(results)).intValue();
               	
                  System.out.print("How many copies would you like to add?: ");
                  response = redo.next();
               	
                  while (!(response.matches("[\\d]+"))) {
                     System.out.println("Invalid input, please type an integer value for the number of copies you want to add.");
                     System.out.print("Please enter the amount of copies to add: ");
                     response = redo.next();
                  }
               
                  (bookList.get(results)).addCopies(response);
                  System.out.println();
                  System.out.println((bookList.get(results)).toString());
               } // end else
            } // end while
         } // end else
         
         return bookList;
      } // end addNewBooks
   
       public static ArrayList<Integer> searchForBook(Object[] list, String response) {
         ArrayList<Integer> bookKeys = new ArrayList<Integer>();
         Scanner decide = new Scanner(System.in);
      	
         bookKeys.clear();
         	
         System.out.println("\nWould you like to search by,");
         System.out.println("********PLEASE TYPE*********");
         System.out.println("* (1) Title                *");
         System.out.println("* (2) Author               *");
         System.out.println("* (3) Barcode              *");
         System.out.println("* (4) End Search           *");
         System.out.print("Please enter one of the numbers above: ");
         response = decide.nextLine(); 
         
         while (!(response.matches("[1-4]{1}"))) {
            System.out.println("Invalid input, please type (1) to search by title, (2) to search by author, (3) to search by barcode");
            System.out.println("**************PLEASE TYPE******************");
            System.out.println("* (1) Title                               *");
            System.out.println("* (2) Author                              *");
            System.out.println("* (3) Barcode                             *");
            System.out.println("* (4) End Search                          *");
            System.out.print("Please enter one of the options above: ");
            response = decide.nextLine();
         } // end while
          
         Scanner searchValue = new Scanner(System.in);     
         
         if (response.equalsIgnoreCase("1")) {
         
            System.out.print("Please type a title to search for: ");        
            response = searchValue.nextLine();
            System.out.println();
            bookKeys = searchMethod(list, response);    	
         	
         } 
         else if(response.equalsIgnoreCase("2")) {
         
            System.out.print("Please type the author name to search for: ");       
            response = searchValue.nextLine();
            System.out.println(); 
         	
            while (!(response.matches("[^\\d]+"))) {
               System.out.println("Invalid author name entry, please enter letters for the author name.");
               System.out.print("Please type the author name to search for: ");
               response = searchValue.nextLine();
            }
         	
            bookKeys = searchMethod(list, response);
         
         }
         else if (response.equalsIgnoreCase("4")) {
            return bookKeys;
         }
         else {
         
            System.out.print("Please type the book barcode to search for: ");        
            response = searchValue.nextLine();
            System.out.println();     	
         	
            while (!(response.matches("[\\d]{5}"))) {
               System.out.println("Invalid barcode entry, please enter a 5 integer number for the barcode.");
               System.out.print("Please type the book barcode to search for: ");
               response = searchValue.nextLine();
            }
         
            bookKeys = searchMethod(list, response);
         
         }
      	
         return bookKeys;
      	
      } // end searchForBook		 
   
       public static ArrayList<Integer> searchMethod(Object[] list, String response) {
         ArrayList<Integer> key = new ArrayList<Integer>();
      	
         key.clear();
      
         for (int i = 0; i <= list.length - 1; i++) {
            if (list[i] instanceof Book && (((Book)list[i]).getBookTitle()).equalsIgnoreCase(response))  
               key.add(new Integer(i));
            if (list[i] instanceof Book && (((Book)list[i]).getAuthor()).equalsIgnoreCase(response)) 
               key.add(new Integer(i));
            if (list[i] instanceof Book && (((Book)list[i]).getBarCode()).equalsIgnoreCase(response)) 
               key.add(new Integer(i));
            if ((list[i] instanceof Faculty && (((Faculty)list[i]).getID()).equalsIgnoreCase(response)))
               key.add(new Integer(i));
            if ((list[i] instanceof Student && (((Student)list[i]).getID()).equalsIgnoreCase(response)))
               key.add(new Integer(i));		
            if (list[i] instanceof CheckOutRecord && (((CheckOutRecord)list[i]).getBorrowerID()).equalsIgnoreCase(response))
               key.add(new Integer(i));  
         } // end for
      	
         return key;
      } // end searchMethod
   	
       public static void checkBookAvail(ArrayList<Book> bookList) {
         ArrayList<Integer> searchResults = new ArrayList<Integer>();
         Object[] searchBookList;
         int results = 0;
         Scanner input = new Scanner(System.in);
         String response = "yes";
       	
         while (response.equalsIgnoreCase("yes")) {
            searchBookList = bookList.toArray();
            searchResults = searchForBook(searchBookList, response);
         
            for (int i = 0; i <= searchResults.size() - 1; i++) {
               results = (searchResults.get(i)).intValue();
               System.out.println((bookList.get(results)).checkForCopies());
            }
         
            if (searchResults.size() == 0) {
               System.out.println("Your book was not found. Would you like to search again?");
               System.out.print("Please type (yes) or (no): ");
               response = input.nextLine();
            
               while (!(response.equalsIgnoreCase("no") || response.equalsIgnoreCase("yes"))) {
                  System.out.println("Please type only (no) or only (yes).");
                  System.out.print("Please type (yes) or (no) to search again: ");
                  response = input.nextLine();
               } // end while
            
            } // end if
         
         } // end while
      	
      } // end checkBookAvail
   	 
       public static void checkOutBook(ArrayList<Book> bookList, ArrayList<User> userList, ArrayList<CheckOutRecord> checkOutRecordList) {
         Scanner input = new Scanner(System.in);
         String response = "";
         Student loggedInStudent;
         Faculty loggedInFaculty;
         ArrayList<Integer> searchResults = new ArrayList<Integer>();
         ArrayList<Integer> resultsIndexes = new ArrayList<Integer>();
         int result = 0;
         int user = 0;
         int userPresentCheck = 0;
         Object[] searchList;
         String rangeCheck = "";
       	
         System.out.println("*******CHECK OUT BOOK INSTRUCTIONS**********");
         System.out.println("*Please type one of the following numbers: *");
         System.out.println("*  (1) Register your info                  *");
         System.out.println("*  (2) Login with ID                       *");
         System.out.println();
       
         System.out.print("Please enter one of the following options: ");
         response = input.next();
       
         while (!(response.matches("[1-2]{1}"))) {
            System.out.println("Invalid entry, please choose one of the three options and type the number below.");
            System.out.println("********PLEASE TYPE*********");
            System.out.println("* (1) Register your info   *");
            System.out.println("* (2) Login with ID        *");
            System.out.print("Please enter one of the options above: ");
            response = input.next();
         }
      	
         if (response.equalsIgnoreCase("1")) 
            userList = addNewUser(userList);
         else {
            System.out.print("Please enter your ID number: ");
            response = input.next();
         
            while (!(response.matches("[\\d]{5}"))) {
               System.out.println("Invalid ID entry, please enter a 5 integer number for the ID.");
               System.out.print("Please enter your ID number: ");
               response = input.next();
            }
         
            System.out.println();
            searchList = userList.toArray();
            searchResults = searchMethod(searchList, response);
         
            user = (searchResults.get(0)).intValue();
            userPresentCheck = searchResults.size();
         
            if (userPresentCheck == 0) {
               System.out.println("No match found, please register yourself by using the (e) command " + "\n" + "at the library manager command interface or, ");
               System.out.println("select 1 in the options menu when typing (c) at the library manager command interface. Goodbye.");
               return;
            }
            else if (userList.get(user) instanceof Student) {
               System.out.println("Thank you for logging in " + ((Student)userList.get(user)).getFirstName());
            }
            else {
               System.out.println("Thank you for logging in " + ((Faculty)userList.get(user)).getFirstName());
            }
         
            response = "yes";
         
            while(response.equalsIgnoreCase("yes")) {
               searchResults.clear();
               searchList = bookList.toArray();
               searchResults = searchForBook(searchList, response);
            
               if (searchResults.size() == 0) {
                  System.out.println("Book could not be found, would you like to search for the book again?");
                  System.out.print("Please type (yes) or (no): ");
                  response = input.next();
               }
               else {
               
                  System.out.println(searchResults.size() + " returned.");
                  System.out.println();
               
                  for (int i = 0; i <= searchResults.size() - 1; i++) {
                     result = (searchResults.get(i)).intValue();
                     
                     if (result >= 0) 
                        System.out.println((bookList.get(result)).toString());
                     resultsIndexes.add(new Integer(result));
                  }
               
                  System.out.println("What book would you like to check out?" + "\n" + "please choose from the list by typing 0 for the first book in the list, 1 for the second and so on.");
                  System.out.print("Enter the number here: ");
                  response = input.next();
                  rangeCheck += searchResults.size();
               
                  while (response.equals(rangeCheck)) {
                     System.out.println();
                     System.out.println("Invalid choice, please enter a number in accordance to the instructions." + "\n" +"for example if the search returns 1 book, please type 0 and nothing higher.");
                     System.out.println();              
                     System.out.println("What book would you like to check out?" + "\n" + "(Type 0, 1, 2, ..., n to choose a book with 0 corresponding to the first book in the list above and so on)");
                     System.out.print("Enter the number here: ");              
                     response = input.next();
                  }
               
                  while (!(response.matches("[\\d]+"))) {
                     System.out.println("Invalid input, please type 0, 1, 2, ..., n in the order of the search results to choose which book to check out.");
                     System.out.print("Please enter 0, 1, 2, ..., n in the order of the search results to choose a book to check out: ");
                     response = input.next();
                  }
               	
                  result = Integer.parseInt(response);
                  result = (resultsIndexes.get(result)).intValue();
               	
                  if (userList.get(user) instanceof Student && (((Student)userList.get(user)).getBookArraySize() < (userList.get(user)).getUpperLimit())) {
                     (bookList.get(result)).subtractCopies();
                     ((Student)userList.get(user)).storeBorrowedBooks(bookList.get(result));
                     checkOutRecordList.add(new CheckOutRecord((bookList.get(result)).getBarCode(), ((Student)userList.get(user)).getID()));
                  } // end if
                  else {
                     if (((Faculty)userList.get(user)).getBookArraySize() < (userList.get(user)).getUpperLimit()) {
                        (bookList.get(result)).subtractCopies();
                        ((Faculty)userList.get(user)).storeBorrowedBooks(bookList.get(result));
                        checkOutRecordList.add(new CheckOutRecord((bookList.get(result)).getBarCode(), ((Faculty)userList.get(user)).getID()));
                     } // end if
                  } // end else
               	
                  if (userList.get(user) instanceof Student && (((Student)userList.get(user)).getBookArraySize() == (userList.get(user)).getUpperLimit()))
                     System.out.println("You have exceeded the limit of books you can borrow please turn in books to check out more.");
                  else {
                     if (userList.get(user) instanceof Faculty && (((Faculty)userList.get(user)).getBookArraySize() == (userList.get(user)).getUpperLimit()))
                        System.out.println("You have exceeded the limit of books you can borrow please turn in books to check out more.");
                  }
               	
                  System.out.println();
                  System.out.print("Done!");
                  System.out.println();
               	
               } // end while	
            } // end else
         } // end else
      
      
      } // end checkOutBook 
   	
       public static void userBooksBorrowed(ArrayList<Book> bookList, ArrayList<User> userList, ArrayList<CheckOutRecord> checkOutRecordList) {
         String response = "";
         Scanner input = new Scanner(System.in);
         ArrayList<Integer> searchResults = new ArrayList<Integer>();
         Object[] searchList;
         int userPresentCheck;
         int result = 0;
      
         System.out.print("Please enter a user ID: ");
         response = input.nextLine();
      
         while (!(response.matches("[\\d]{5}"))) {
            System.out.println("Invalid ID entry, please enter a 5 integer number for the ID.");
            System.out.print("Please enter your ID number: ");
            response = input.nextLine();
         }
         
         System.out.println();
         searchList = userList.toArray();
         searchResults = searchMethod(searchList, response);
         
         result = (searchResults.get(0)).intValue();
         userPresentCheck = searchResults.size();
         searchResults.clear();
      		
         if (userPresentCheck == 0) {
            System.out.println("No match found, please register yourself by using the (e) command " + "\n" + "at the library manager command interface.");
            return;
         }
         else if (userList.get(result) instanceof Student) {
            searchList = checkOutRecordList.toArray();
            searchResults = searchMethod(searchList, response);
         	
            System.out.println("This user borrowed: ");
         	
            for (int i = 0; i <= searchResults.size() - 1; i++) {
               result = (searchResults.get(i)).intValue();
               (checkOutRecordList.get(result)).findBookBorrowed(bookList);
            }
         } // end if
         else {
            searchList = checkOutRecordList.toArray();
            searchResults = searchMethod(searchList, response);
         	
            System.out.println("This user borrowed: ");
         	
            for (int i = 0; i <= searchResults.size() - 1; i++) {
               result = (searchResults.get(i)).intValue();
               (checkOutRecordList.get(result)).findBookBorrowed(bookList);
            }
         } // end else
         
         System.out.println();
      		
      } // end userBooksBorrowed
      
       public static void findBooksPastDue(ArrayList<User> userList, ArrayList<CheckOutRecord> checkOutRecordList) {
         boolean returnDatePast = false;
         ArrayList<Integer> searchResults = new ArrayList<Integer>();
         Object[] list = userList.toArray();
         int result = 0;
      	
			System.out.println();
			System.out.println("Users who have books past due (Blank list means no past due books): "); 
		
         for (int i = 0; i <= checkOutRecordList.size() - 1; i++) {
         
            returnDatePast = (checkOutRecordList.get(i)).borrowCompareToReturn();
         
            if (returnDatePast) {
               searchResults = searchMethod(list, (checkOutRecordList.get(i)).getBorrowerID());
            
               for (int j = 0; j <= searchResults.size() - 1; j++) {
                  result = (searchResults.get(j)).intValue();
               
                  if (userList.get(result) instanceof Student)
                     System.out.println(((Student)userList.get(result)).getFirstName() + " " + ((Student)userList.get(result)).getLastName() + " " + ((Student)userList.get(result)).getEmailAddress());
                  else
                     System.out.println(((Faculty)userList.get(result)).getFirstName() + " " + ((Faculty)userList.get(result)).getLastName() + " " + ((Faculty)userList.get(result)).getEmailAddress());
               
               } // end inner for
            } // end if
         } // end outer for
      
		System.out.println();
		
      } // end findBooksPastDue
   
       public static void saveInfo(ArrayList<Book> bookList, ArrayList<CheckOutRecord> checkOutRecordList, ArrayList<User> userList) throws Exception {;
      
         File bookListFile = new File("librarybooks.txt");
         File userListFile = new File("userinfo.txt");
         File checkOutRecordListFile = new File("checkoutrecords.txt");
      
         PrintWriter saveBookList = new PrintWriter(bookListFile);
      
         for (int i = 0; i <= bookList.size() - 1; i++) 
            saveBookList.println((bookList.get(i)).getBookTitle() + ", " + (bookList.get(i)).getBarCode() + ", " + (bookList.get(i)).getAuthor() + ", " + (bookList.get(i)).getPrice() + ", " + (bookList.get(i)).getCopies());
      
         saveBookList.close();
      
         PrintWriter saveUserList = new PrintWriter(userListFile);
      
         for (int j = 0; j <= userList.size() - 1; j++) {
            if (userList.get(j) instanceof Student) 
               saveUserList.println(((Student)userList.get(j)).getIdentifier() + ", " + ((Student)userList.get(j)).getFirstName() + ", " + ((Student)userList.get(j)).getLastName() + ", " + ((Student)userList.get(j)).getID() + ", " + ((Student)userList.get(j)).getPhoneNumber() + ", " + ((Student)userList.get(j)).getEmailAddress() + ", " + ((Student)userList.get(j)).getDormitoryNumber());
            else
               saveUserList.println(((Faculty)userList.get(j)).getIdentifier() + ", " + ((Faculty)userList.get(j)).getFirstName() + ", " + ((Faculty)userList.get(j)).getLastName() + ", " + ((Faculty)userList.get(j)).getID() + ", " + ((Faculty)userList.get(j)).getPhoneNumber() + ", " + ((Faculty)userList.get(j)).getEmailAddress() + ", " + ((Faculty)userList.get(j)).getOfficeNumber());
         }
      
         saveUserList.close();
      
         PrintWriter saveCheckOutRecordList = new PrintWriter(checkOutRecordListFile);
      
         for (int k = 0; k <= checkOutRecordList.size() - 1; k++)
            saveCheckOutRecordList.println((checkOutRecordList.get(k)).getBarCode() + ", " + (checkOutRecordList.get(k)).getBorrowerID() + ", " + (checkOutRecordList.get(k)).getBorrowDate() + ", " + (checkOutRecordList.get(k)).getReturnDate());
      
         saveCheckOutRecordList.close();
      
      } // end saveInfo
   
   } // end class