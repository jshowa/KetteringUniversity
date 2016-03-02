import java.util.*;

public class Faculty extends User {
private String identifier; // identifier used when creating a new user so when the new user info is saved the identifier (0 or 1) is present
private String firstName; // the first name of the current faculty object
private String lastName; // the last name of the current faculty object
private String ID; // the identification number of the current faculty object
private String phoneNumber; // the phone number of the current faculty object
private String emailAddress; // the email address of the current faculty object
private String officeNumber; // the office number of the current faculty object
private ArrayList<Book> books = new ArrayList<Book>(); // book array list used to hold the books the faculty member will check out
private final int BORROW_LIMIT = 60; // maximum limit constant a faculty member can check out

Faculty() { // faculty 0 arg constructor 1
}

Faculty(String[] facultyProperties) { // faculty constructor 2 used when reading from the files
identifier = facultyProperties[0];
firstName = facultyProperties[1];
lastName = facultyProperties[2];
ID = facultyProperties[3];
phoneNumber = facultyProperties[4];
emailAddress = facultyProperties[5];
officeNumber = facultyProperties[6];
}

// faculty constructor 3 used to create new faculty information to save to the file
Faculty(String identifier, String firstName, String lastName, String ID, String phoneNumber, String emailAddress, String officeNumber) { 
this.identifier = identifier;
this.firstName = firstName;
this.lastName = lastName;
this.ID = ID;
this.phoneNumber = phoneNumber;
this.emailAddress = emailAddress;
this.officeNumber = officeNumber;
}

public String getIdentifier() { // accessor used to get the identifier of the faculty object when being saved to the file
return identifier;
}

public String getFirstName() { // acessor used to get the first name of the current faculty object
return firstName;
}

public String getLastName() { // accessor to get the last name of the current faculty object
return lastName;
}

public String getID() { // accessor to get the identification number of the current faculty object
return ID;
}

public String getPhoneNumber() { // accessor to get the phone number of the current faculty object
return phoneNumber;
}

public String getEmailAddress() { // accessor to get the email of the current faculty object
return emailAddress;
}

public String getOfficeNumber() { // accessor to get the office number of the current faculty object
return officeNumber;
}

public int getBookArraySize() { // accessor used to get the size of the book array list used to store the books a faculty member borrows
return (this.books).size();
}

public void storeBorrowedBooks(Book borrowedBook) { // mutator used to add the books a faculty object borrows to the faculty object array list
(this.books).add(borrowedBook);
}

public int getUpperLimit() { // accessor used to get borrow limit of faculty member, abstracted into User
return BORROW_LIMIT;
}

} // end class Faculty