/* Jacob S. Howarth
 * CS-101-07L, Spring 2007
 * Lab 5 -- Sudoku Check
 */
 
 /* This program will check sudoku puzzles and notify the user
  * wether it contains a partial solution, a solution, or is
  * an invalid solution.
  */
  
  import java.util.Scanner;
  import java.io.File;
  
  class SudokuCheck {
  
  public static void main (String [] args) throws Exception {
	 int [][] sudokuTable = new int[9][9];  // Two dimensional 9 by 9 grid
	 Scanner input;                         // Scanner variable
	 boolean result;                        // Boolean variable for result check
	 
	 // Print Identification Information
	 System.out.println(" Jacob Howarth ");
    System.out.println(" CS-101-07L, Spring 2007 ");
    System.out.println(" Lab 5 -- Sudoku Checker ");
	 
	 System.out.println(); // spacing between identification info and file read in

	 input = new Scanner(new File (args[0])); // initialize scanner for file
	 
    while(input.hasNextInt()) { // loops for each sudoku puzzle in file	   
	 
	   sudokuTable = arrayCreate(input); // creates sudoku puzzle form the numbers in the file
	   printArray(sudokuTable); // prints each the sudoku puzzle
	 
	   result = checkColumn(sudokuTable); // checks for repeated numbers in the columns of each puzzle 
	   if (result)
	   result = checkRow(sudokuTable); // checks for repeated numbers in the rows of each puzzle
	   if (result)
	   result = miniGrid(sudokuTable); // checks the 3 by 3 sections of each sudoku puzzle for repeated numbers
	 
	 if (result) {  // print if the puzzle is correct or false
	   System.out.println("The Sudoku puzzle is correct."); 
	 }
	 else {
	   System.out.println("The Sudoku puzzle is incorrect.");
	 }
    System.out.println();
  }
  } // close main
	 
	 public static int[][] arrayCreate(Scanner input) { // Creates the array from input
	    int [][] sudokuTable = new int[9][9];
	 
	    for(int i = 0; i < sudokuTable.length; i++) { // generates the rows for each array
	      for(int j = 0; j < sudokuTable[i].length; j++) { // generates the columns for each array
		     sudokuTable[i][j] = input.nextInt();
		   }
	    }
	 return sudokuTable; // returns finished sudoku table
	 }// close arrayCreate
	 
	 public static void printArray(int[][] sudokuTable) { // Prints the array in the specification for output
	   
		for(int row = 0; row < sudokuTable.length; row++) { // prints the row for each puzzle
		  for(int column = 0; column < sudokuTable[row].length; column++) { // prints the column for each puzzle
			 System.out.print(sudokuTable[row][column] + " "); // puts space after each number
			   
		    if (column == 2 || column == 5) // puts pipe after each 3 columns
				System.out.print("| ");
				
			 if (row == 2 && column == 8 || row == 5 && column == 8) // puts dashes after each 3 rows
			   System.out.print("\n------+-------+------");
 
		  }
			 System.out.println(); // spaces each puzzle
	   }
	 } // close printArray
	 
	 public static boolean checkRow(int[][] sudokuTable) { // Checks individual rows for error, if false == table broken, true == table okay
	   boolean[] flagArray = new boolean[10]; // generates check array
	 	 
	   for(int i = 0; i < sudokuTable.length; i++) { 
	     for(int k = 0; k < flagArray.length; k++) { // sets boolean check array to all true
		    flagArray[k] = true;
		  }
  	   
		for(int j = 0; j < sudokuTable[i].length; j++) { // checks rows 
        if (flagArray[sudokuTable[i][j]] == true && sudokuTable[i][j] != 0) { // compares each position in the array to check array index and stores false in each one
	       flagArray[sudokuTable[i][j]] = false;
		}
		else if (flagArray[sudokuTable[i][j]] == false) { // if repeated number, the index will be flagged and the puzzle will return incorrect
		  return false;
		} 
		}
		}
		return true;
	 } // close checkRow
	 
	 public static boolean checkColumn(int[][] sudokuTable) { // Check individual columns for error, if false == table broken, true == table okay
	   boolean[] flagArray = new boolean[10];  // generates check array
	 	 
	   for(int i = 0; i < sudokuTable.length; i++) { 
	     for(int k = 0; k < flagArray.length; k++) { // sets boolean check array to all true
		  flagArray[k] = true;
		}
  	   
		for(int j = 0; j < sudokuTable[i].length; j++) { // checks columns
        if (flagArray[sudokuTable[j][i]] == true && sudokuTable[j][i] != 0) { // compares each position in the array to check array index and stores false in each one
		  flagArray[sudokuTable[j][i]] = false;
		}
		else if (flagArray[sudokuTable[j][i]] == false) { // if repeated number, the index will be flagged and the puzzle will return incorrect
		  return false;
		} 
		}
		}
		return true;
		} // close checkColumn
		
	 public static boolean miniGridHelper(int[][] sudokuTable, int row,int column) { // creates flag array and mini grids
		boolean[] flagArray = new boolean[10]; // check array creation
		
		for(int k = 0; k < flagArray.length; k++) { // populate check array with all true booleans
		  flagArray[k] = true;
		}
		
		for(int i = 0; i < 3; i++) { // generates each mini grid 3 by 3 row
		  for(int j = 0; j < 3; j++) { // generates each mini grid 3 by 3 column
		  
		    if (flagArray[sudokuTable[j+row][i+column]] == true && // checks the rows and columns of each mini grid and stores the positions in the same check array index
		      sudokuTable[j+row][i+column] != 0) {
		
		      flagArray[sudokuTable[j+row][i+column]] = false; // changes all the check array indexes to false depending on the mini grid position
		  }
		  else if (flagArray[sudokuTable[j+row][i+column]] == false) { // if a number is repeated in a mini grid the value will equal false and return incorrect
		    return false;
		  } 
		  }
		  }
		  return true;
	 } // close miniGridHelper

	 public static boolean miniGrid(int[][] sudokuTable) { // Checks 3 by 3 grid for repeated numbers
		boolean v = true;
		
		for(int row = 0; row < sudokuTable.length; row += 3) { // moves to each 3 by 3 grid
		  for(int column = 0; column < sudokuTable[row].length; column += 3){
		  
		  v = miniGridHelper(sudokuTable, column, row); // calls the miniGridHelper method to for validation of each 3 by 3
		  
	     if(!v){ // if v is not true return true and if v is not false return v 
		    return v;
		  }
		  }
		}
		  return v;
    } // close miniGrid
	 } // close SudokuCheck