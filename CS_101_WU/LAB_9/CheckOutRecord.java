import java.util.*;

public class CheckOutRecord {
private String barCode; // holds the barcode of a book in the check out record object
private String borrowerID; // holds the borrower identification number of a student or faculty member who borrowed the book
private String borrowDate; // holds the borrow date of the book
private String returnDate; // holds the return date of the book

CheckOutRecord() { // check out record 0 arg constructor
}

// constructor 2 that creates check out records from the files
CheckOutRecord(String[] checkOutRecordProp) {
this.barCode = checkOutRecordProp[0];
this.borrowerID = checkOutRecordProp[1];
this.borrowDate = checkOutRecordProp[2]; 
this.returnDate = checkOutRecordProp[3];
}

CheckOutRecord(String barcode, String borrowerID) { // constructor 3 that creates a check out record for a book checked out by a student or faculty 
this.barCode = barcode;
this.borrowerID = borrowerID;
this.borrowDate = this.getCurrentDate(); // when a student borrows a book, the current date is the borrow date
this.returnDate = this.getNewReturnDate(); // the return date was decided to be 7 days after the borrow date
}

public String getReturnDate() { // accessor to get return date of a book in the check out record object
return returnDate;
}

public String getBarCode() { // accessor to get the bar code of a book in the check out record object
return barCode;
}

public String getBorrowerID() { // accessor to get the borrower identification number of a student or faculty member in the check out record object
return borrowerID;
}

public String getBorrowDate() { // accessor to get the borrow date of the current check out record object
return borrowDate;
}

public String getCurrentDate() { // accessor to generate the current date in string form when a user borrows a book
Calendar currentDate = new GregorianCalendar();
return (currentDate.get(Calendar.MONTH) + 1) + "/" + currentDate.get(Calendar.DATE) + "/" + currentDate.get(Calendar.YEAR);
}

public Calendar getCurrentDateCal() { // accessor used to generate a current date in date object form used in the comparison method below
Calendar currentDate = new GregorianCalendar();
return currentDate;
}

public void findBookBorrowed(ArrayList<Book> bookList) { // finds a borrowed book by comparing the barcode of the checkout record to a barcode of a book object
for (int i = 0; i <= bookList.size() - 1; i++) {

if ((this.barCode).equals((bookList.get(i)).getBarCode())) 
System.out.println((bookList.get(i)).getBookTitle()); // prints the book title when a comparison is found

}
} // end findBookBorrowed

public String getNewReturnDate() { // returns a new return date by adding 7 days to the borrow date when a book is check out
String[] list; // array holds the split strings
String delims = "[/]"; // dates are read in by the delimiter / for example 12/2/2007, therefore in order to split the borrow string, and add 7 days to the number of days, this split reg exp must be used as the delimiter
String newReturnDate = this.borrowDate; // the borrow date of a book is stored in the new return date so it can be used latter to add days to it
int addDays = 0; // holds the added days to the borrow date

list = newReturnDate.split(delims);
for (int i = 0; i <= list.length - 1; i++) 
list[i] = list[i].trim(); // trims access white space off the split strings

addDays = Integer.parseInt(list[1]) + 7; // adds 7 days to the borrow date string 
list[1] = ""; // this spot in the split strings array holds the day value
list[1] += addDays; // the added days are concatenated into the day spot of the array
newReturnDate = ""; 

for (int j = 0; j <= list.length - 1; j++) { // the for loop returns the days in dash form (for example 12/2/2007)
if (j == 2)
newReturnDate += list[j];
else
newReturnDate += list[j] + "/";
}

return newReturnDate;
}

public boolean borrowCompareToReturn() { // this method is used primarily for returning user information of a book past due it compares the return date to the current date and sees if it's more or less then the return date
String compare = this.getCurrentDate(); // string that holds current date so we can see if both the return date and current date are equal
Calendar currentDate; // used to see if the current date is greater than the return since a date before the return does not equal the return date and may exhibit faulty output on this case

if (compare.equals(this.returnDate)) 
return true;

else {
currentDate = this.getCurrentDateCal(); // calendar object holds the current date
String delims = "[/]"; // since the return date is a string it cannot be compared by > or < so it must be split by this delimiter pattern and parsed into integer form
String[] date; // array holds split string
int[] dateIntegers = new int[3]; // holds the parsed month, day, and year number of the return date
date = (this.returnDate).split(delims);

dateIntegers[0] = Integer.parseInt(date[0]); // parse the month to an integer and store it in the zero index spot
dateIntegers[1] = Integer.parseInt(date[1]); // parse the day to an integer and store it in the first index spot
dateIntegers[2] = Integer.parseInt(date[2]); // parse the year to an integer and store it in the second index spot

if ((((currentDate.get(Calendar.MONTH) + 1) > dateIntegers[0]) || ((currentDate.get(Calendar.MONTH) + 1) < dateIntegers[0])) && (currentDate.get(Calendar.YEAR) > dateIntegers[2])) 
return true; // if the current month is greater or less than the return date month and the current year is greater than the return year then the book is past due
else if (((currentDate.get(Calendar.MONTH) + 1) > dateIntegers[0]) && (currentDate.get(Calendar.YEAR) == dateIntegers[2]))
return true; // if the current month is greater than the return month and the year is the same then the book is past due
else if (((currentDate.get(Calendar.MONTH) + 1) == dateIntegers[0]) && (currentDate.get(Calendar.YEAR) == dateIntegers[2]) && (currentDate.get(Calendar.DATE) > dateIntegers[1]))
return true; // if the current month and year are the same as the return date but the day of the month is greater than the day of the month for the return date, the book is past due 
else 
return false;
} // end else

}

} // end of class