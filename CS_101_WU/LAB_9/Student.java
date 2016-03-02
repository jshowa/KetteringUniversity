import java.util.*;

public class Student extends User {
private String identifier; // variable used to store identifier (0 or 1) for student object
private String firstName; // contains first name of the current student object
private String lastName; // contains last name of the current student object
private String ID; // contains the identification number of the current student object
private String phoneNumber; // contains the phone number of the current student object 
private String emailAddress; // contains the email address of the current student object
private String dormitoryNumber; // contains the dorm number of the current student object
private ArrayList<Book> books = new ArrayList<Book>(); // contains the books a student borrows 
private final int BORROW_LIMIT = 50; // limit constant of the maximum number of books a student can borrow

Student() { // 0 arg constructor for student object
}

Student(String[] studentProperties) { // constructor 2 used to create student objects from a read in file
identifier = studentProperties[0];
firstName = studentProperties[1];
lastName = studentProperties[2];
ID = studentProperties[3];
phoneNumber = studentProperties[4];
emailAddress = studentProperties[5];
dormitoryNumber = studentProperties[6];
}

// constructor 3 used to create a new student object and save it to the file
Student(String identifier, String firstName, String lastName, String ID, String phoneNumber, String emailAddress, String dormitoryNumber) {
this.identifier = identifier;
this.firstName = firstName;
this.lastName = lastName;
this.ID = ID;
this.phoneNumber = phoneNumber;
this.emailAddress = emailAddress;
this.dormitoryNumber = dormitoryNumber;
}

public String getIdentifier() { // accessor used to get the identifier of the student in order to read the files correctly when the program reruns
return identifier;
}

public String getFirstName() { // accessor to get the first name of the current student object
return firstName;
}

public String getLastName() { // accessor to get the last name of the current student object
return lastName;
}

public String getID() { // accessor to get the id of the current student object
return ID;
}

public String getPhoneNumber() { // accessor to get the phone number of the current student object
return phoneNumber;
}

public String getEmailAddress() { // accessor to get the email address of the current student object
return emailAddress;
}

public String getDormitoryNumber() { // accessor to get the dorm room number of the current student object
return dormitoryNumber;
}

public int getBookArraySize() { // accessor used to get the size of the arraylist of books the student checked out
return (this.books).size();
}

public void storeBorrowedBooks(Book borrowedBook) { // mutator that adds books to the current student objects check out list
(this.books).add(borrowedBook);
}

public int getUpperLimit() { // used to get the maximum limit of books a student can borrow
return BORROW_LIMIT;
}


} // end student class