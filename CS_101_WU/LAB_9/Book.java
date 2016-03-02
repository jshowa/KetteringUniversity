    public class Book {
      private String bookTitle; // contains the title of the current book object
      private String barCode; // contains the barCode of the current book object
      private String author; // contains the author of the current book object
      private double price; // contains the price of the current book object
      private int copies; // contains the copies of the current book object
   
       Book() { // no-arg constructor for book object
      } // end constructor 1
   
       Book(String[] bookProperties) { // constructor for book objects read from client program files
         bookTitle = bookProperties[0];
         barCode = bookProperties[1];
         author = bookProperties[2];
         try { // try catch to check if the price and copies are able to be parsed and there was no mistake in putting them in the file.
            price = Double.parseDouble(bookProperties[3]);
            copies = Integer.parseInt(bookProperties[4]);
         }
             catch (NumberFormatException ex) { // exit the program if an error due to potential consequences of working with a file that may be corrupt
               System.out.println("File error: Please check the file for bad data.");
               System.out.print("Exiting program.");
               System.exit(0);
            }
      } // end constructor 2
   
       Book(String bookTitle, String barCode, String author, String price, String copies) { // constructor 3 used to create a new book object when a book is added using the command interface
         this.bookTitle = bookTitle;
         this.barCode = barCode;
         this.author = author;
         this.price = Double.parseDouble(price);
         this.copies = Integer.parseInt(copies);
      } 
   
       public String getBookTitle() { // accessor for the book title of the current book object
         return bookTitle;
      }
   
       public String getBarCode() { // accessor for the barcode of the current book object
         return barCode;
      }
   
       public String getAuthor() { // accessor for the author of the current book object
         return author;
      }
   
       public double getPrice() { // accessor for the price of the current book object
         return price;
      }
   	
       public int getCopies() { // accessor for the number of copies of the current book object
         return copies;
      }
   	
       public void addCopies(String newCopies) { // special mutator used when copies of a current book are added if the book is low on copies
         this.copies += Integer.parseInt(newCopies);
      }
   	
       public void subtractCopies() { // special mutator used to subtract the copy of a book object when a book is borrowed by a student of faculty
         if (this.copies > 0)
            this.copies--;
         else // throw an error to prevent copies from becoming a negative number
            System.out.println("There are no more copies of this book, please add more."); 
      }
   	
       public String checkForCopies() { // method used to check for the copy amount of a book
         if (this.copies == 0) {
            this.toString();
            return "\nThere are no more copies, please add more.";
         }
         else
            return this.toString(); // if the number of copies of the book object != 0 then call the toString() and print book info
      }
   
       public String toString() { // Return a print out of a book object with all information
         return "Book:\n" + "Title: " + this.bookTitle + "\n" + "Barcode #: " + this.barCode + "\n" + "Author: " + this.author + "\n" + "Cost: " + this.price + "\n" + "Number of Copies: " + this.copies + "\n";
      } // end toString
   
   } // end class
