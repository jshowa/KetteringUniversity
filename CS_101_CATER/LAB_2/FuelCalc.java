/* Jacob Howarth
 * CS-101-07L, Spring 2007
 * Lab 1 -- Remaining Fuel Calculator
 */

/* This program will calculate the amount of fuel remaining after a
 * vehicle of given fuel usage rate (in miles per gallon (MPG)) and
 * tank size (in gallons) has been driven a given distance (in miles).
 * The fuel usage and tank size will be given on the command line,
 * while the distance driven will be prompted for and read from
 * standard input.
 */


import java.io.*;

class FuelCalc {

   public static void main (String [] args) throws IOException {
      double MPG;       // fuel usage rate, miles per gallon
      double tankSize;  // tank size, in gallons
      double dist;      // distance driven, in miles

      // Print Personal Identification Information
      System.out.println("Jacob Howarth");
      System.out.println("CS-101-07L, Spring 2007");
      System.out.println("Lab 1 -- Remaining Fuel Calculator");

      // set up standard input for reading
      BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));
		
	   // get MPG and tank_size from command line     
		MPG = Double.parseDouble(args[0]);
      tankSize = Double.parseDouble(args[1]);
	   
		// the if...else statement does stops program if MPG input is <= 0. This accounts for negative MPG.
		if (MPG <= 0) {
	   System.out.println("Enter value greater than 0 for MPG");
		}
		else {
	   
		// the second if...else statement stops program if tankSize is <= 0. This accounts for negative and 0 capacity.
		if (tankSize <= 0) {
	   System.out.println("Enter value greater than 0 for tankSize");
		}
		else {
		
		// read distance driven from standard input
      System.out.print("Enter distance driven: ");
      dist = Double.parseDouble(stdin.readLine());
      
		/* this while statement accounts for negative and 0 distance logic errors and allows
		 * distance re-input until distance is positive.
		 */ 
		while (dist <= 0) {
		System.out.println("Distance cannot be negative or zero");
		System.out.print("Enter distance driven: ");
      dist = Double.parseDouble(stdin.readLine());
		}
		
	   // calculate fuel remaining
      double remaining = tankSize - dist / MPG;
		
		/* This statement doesn't allow negative fuel remaining amounts and prints this statement
		 * if the remaining fuel is zero or negative based off dist * tankSize.
		 */
		if (remaining <= 0) {
		System.out.println(" You'll be out of fuel! Better fill'er up!");
		}
		else {
		
		// Print answer
		System.out.println();
      System.out.print("After driving " + dist);
      System.out.println(" miles in a vehicle having a " + tankSize);
      System.out.print(" gallon tank and fuel efficiency of " + MPG);
      System.out.println(" miles per gallon, you have " + remaining);
      System.out.println(" gallons of gas remaining.");
	   
	 }// 1st else statment
   }// 2nd else statment
  }// 3rd else statment
 } // method main
} // class FuelCalc