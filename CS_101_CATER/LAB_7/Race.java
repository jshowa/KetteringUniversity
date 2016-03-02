/* Jacob S. Howarth
 * CS-101-07L, Spring 2007
 * Lab 6 -- Tortoise and Hare
 */
 
/* This lab will simulate a race between two objects, a tortoise and
 * a hare. The race will last a few nanoseconds and the output will
 * be each of the hare and tortoises positions.
 */
 
 public class Race { // Race class open
 
 static Hare h = new Hare(); // create Hare object
 static Tortoise t = new Tortoise(); // create Tortoise object
 
  public static void main(String[] args) { // main method open
		
	// Print Identification Information
	System.out.println("Jacob Howarth");
	System.out.println("CS-101-07L, Spring 2007");
	System.out.println("Lab 6 -- Tortosie and Hare");
	
	// Print indicator for start of the race
	System.out.println("\nBANG!!! AND THERE OFF!!!");
	
	while (h.getPosition() < 70 && t.getPosition() < 70) { // move both Tortoise and Hare until their position reaches 70 squares
	  move();
	
	  if (h.getPosition() >= 70 || t.getPosition() >= 70) { // break loop if Tortoise or Hare position is greater than or equal to 70 squares
	    break;
	  }
	
	  printPosition(); // print both the Tortoise and Hare's position on one line during each loop iteration
	} // end while loop
	
	if (t.getPosition() >= 70 && h.getPosition() < 70) { // if the Tortoise's position is greater than or equal to 70 and the Hare's is less, print Tortoise wins
	  System.out.println("TORTOISE WINS!!! YAY!!!");
	}
	else if (h.getPosition() >= 70 && t.getPosition() < 70) { // if the Hare's position is greater than or equal to 70 and the Tortoise's is less, print Hare wins
	  System.out.println("Hare wins. Yuch.");
	}
	else { // if both the Tortoise and the Hare are greater than or equal to 70 and none of them are behind, print a tie.
	  System.out.println("It's a tie.");
	} 

	} // close main method
	
	public static void move() { // move method that moves both the Tortoise and Hare in each class
	  h.hareMove(); // call move method for Hare from Hare class
	  t.tortoiseMove(); // call move method for Tortoise from Tortoise class
	} // close move() method
	
	public static void printPosition() { // open printPosition() method
	  for (int i = 1; i < 71; i++) { // for loop that checks where each animals position is so it can be marked
	 
	    if ((t.getPosition() == h.getPosition()) && i == t.getPosition()) { // if the positions are equal, Tortoise bites Hare
	      System.out.print("OUCH!!!");
			i += 5; // decrement loop iteration due to the character length of OUCH!!!
	    }  
	    else if (h.getPosition() == i) { // if Hare is equal to i in loop iteration i, mark Hare's position in race
	      System.out.print("H");
	    }
	    else if (t.getPosition() == i) { // if Tortoise is equal to i in loop iteration i, mark Tortoise's position in race
	      System.out.print("T");
	    }
		 else { // if no animal is at any position i, mark i with white space
		   System.out.print(" ");
		 }
     }
	  System.out.println(); // print new line for each position race marker
	} // close printPosition() method
 } // close Race class