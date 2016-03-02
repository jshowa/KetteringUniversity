import java.util.Random; // imports Random class for precentage movement system

class Tortoise { // Tortoise object creation
  private int position; // position data field of object
  
  public Tortoise() { // constructor method, assigns starting position of Tortoise
    position = 1; // Starting position 
  } // close Tortoise()
  
  public void tortoiseMove() { // Action method that moves tortoise by a random number % system
    Random random2 = new Random(); // new Random class instance
	 int randomNum2 = random2.nextInt(9); // Initialize random number generator
	 
	 if (0 <= randomNum2 && randomNum2 <= 4) { // Fast plod, increment position by 3, 50% chance
	   position += 3;
	 }
	 else if (5 <= randomNum2 && randomNum2 <= 6) { // Slip, decrement position by 6, 20% chance
	   position -= 6;
	 }
	 else { // Slow plod, increment position by 1, 30% chance
	   position += 1;
	 }
	 
	 if (position < 0) { // If position is negative, return Tortoise to start
	   position = 1;
	 }
  } // close tortoiseMove
  
  public int getPosition() { // Accessor method that returns Tortoise's current position
    return position;
  } // close getPosition
} // close Tortoise class