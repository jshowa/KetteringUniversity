import java.util.Random; // imports Random class for precentage movement system

class Hare { // Hare object creation
  private int position; // position data field of object hare
  
  public Hare() { // Hare constructor, initializes default values of a Hare instance
    position = 1; // Starting position
  } // close Hare()
  
  public void hareMove() { // Action method that moves Hare by a random number % system
    Random random1 = new Random(); // new Random class instance
	 int randomNum1 = random1.nextInt(9); // Intialize Random number generator
	 
	 if (0 <= randomNum1 && randomNum1 <= 1) { // Sleep, no movement, 20% chance
	 }
	 else if (2 <= randomNum1 && randomNum1 <= 3) { // Big hop, increment position 9 squares, 20% chance
	   position += 9;
	 }
	 else if (randomNum1 == 4) { // Big slip, decrement position by 12 squares, 10% chance
	   position -= 12;
	 }
	 else if (5 <= randomNum1 && randomNum1 <= 7) { // Small hop, increment position by 1 square, 30% chance
	   position += 1;
	 }
	 else { // Small slip, decrement position by 2 squares, 20% chance
	   position -= 2;
	 }
	 	
	 if (position < 0) { // if the position is negative, move object back to start
	   position = 1;
	 }
  } // close hareMove()
  
  public int getPosition() { // Accessor method that returns Hare's position
    return position;
  } // close getPosition()
} // close Hare class