/**
*
*Author: Jacob S. Howarth
*
*Assignment #4
*
*Main.java
*
*This class contains the main method as well as the functions necessary to run the knight tours
*
*/
import java.util.*;

public class KnightTour {
	public static void main(String[] args) {
	
		Scanner input = new Scanner(System.in);
		String[] userInput = null;
		String[] userInput1;
		String check = "";
		int[][] board;
		boolean success = false;
		int moveCount = 0, row, coloumn;
		
		//1. Select board size
		do {
			System.out.print("Please enter the board dimensions (ex. input: \"10x10\"): ");
			check = input.nextLine();

			if (check.length() > 0 && !(check.matches("[\\d]{1,2}x[\\d]{1,2}")))
				System.out.println("Error, please re-enter board dimensions");
			else
				userInput = check.split("x");	
		}
		while(!(check.matches("[\\d]{1,2}x[\\d]{1,2}")));
		
		board = new int[Integer.parseInt(userInput[0])][Integer.parseInt(userInput[1])];
		board = fillBoard(board); // fill board with all zero's
		
		//2. Select starting square
		do {
			System.out.print("Please enter a starting square for the knight (ex. input: \"1,1\"): ");
			check = input.nextLine();	
			
			if (check.length() > 0 && !(check.matches("[\\d]{1,2},[\\d]{1,2}")))
				System.out.println("Error, please re-enter board dimensions");
			else
				userInput = check.split(",");
		}
		while(!(check.matches("[\\d]{1,2},[\\d]{1,2}")));
			
			
		row = Integer.parseInt(userInput[0]) - 1; // set row and coloumn of desired start square
		coloumn = Integer.parseInt(userInput[1]) - 1; 
		
		if(!(knightMove(board, row, coloumn, moveCount, success))) // if tour's cannot be completed.
			System.out.println("NO SOLUTION EXISTS.");
	}
	
	/**
	*Recursive method used to move the position of the knight
	*
	* @param board     A two dimensional array that the knight will move in
	* @param row       The current row of the knights position on the board
	* @param coloumn   The current coloumn of the knights position on the board
	* @param moveCount A value that keeps the knights movement number and acts as a marker for each square
	* @param success   A flag variable indicating the success of a move or completed tour
	*
	* @return   boolean flag variable indicating a successful knight move or completed tour
	*/
	public static boolean knightMove(int[][] board, int row, int coloumn, int moveCount, boolean success) {
		
		boolean done = success;
		
		if (checkValidPosition(board, row, coloumn)) {
			
			moveCount++;
			board[row][coloumn] = moveCount;
			
			try {
				Thread delay = new Thread();
				printBoard(board); // initiate thread delay for each placement of the knight
				delay.sleep(1000);
			}
			catch (Exception ex) {
			}		
		
			if (moveCount == board[row].length * board.length) { // if moveCount = board dimensions m x n then tour is complete
				System.out.println("FINAL SOLUTION: ");
				printBoard(board);
				done = true;
			}
			else {
				done = knightMove(board, row - 2, coloumn - 1, moveCount, success); // list of all eight positions a knight can move too.
					if (!done) 
						done = knightMove(board, row - 1, coloumn - 2, moveCount, success);
					if (!done)
						done = knightMove(board, row + 1, coloumn - 2, moveCount, success);
					if (!done)
						done = knightMove(board, row + 2, coloumn - 1, moveCount, success);
					if (!done)
						done = knightMove(board, row + 2, coloumn + 1, moveCount, success);
					if (!done)
						done = knightMove(board, row + 1, coloumn + 2, moveCount, success);
					if (!done)
						done = knightMove(board, row - 1, coloumn + 2, moveCount, success);
					if (!done)
						done = knightMove(board, row - 2, coloumn + 1, moveCount, success);
			}
			
			if (!done) { // if all positions are not valid due to being marked or out of bounds, decrement move count and back track
				moveCount--;
				board[row][coloumn] = 0;
				try {
					Thread delay = new Thread();
					printBoard(board); // print each back track by initiating thread delay
					delay.sleep(1000);
				}
				catch (Exception ex) {
				}	
			}		
				
		}
		
		return done;
	}
	
	/**
	*Checks if the position the knight will move to is not marked and not out of the array bounds
	*
	* @param board     A two dimensional array that the knight will move in
	* @param row       The current row of the knights position on the board
	* @param coloumn   The current coloumn of the knights position on the board
	*
	* @return   boolean flag variable indicating a valid movement for the knight available on the board
	*/
	public static boolean checkValidPosition(int[][] board, int row, int coloumn) {
		
		boolean valid = false;
		
		if (row >= 0 && row < board.length && coloumn >= 0 && coloumn < board[row].length)
			if (board[row][coloumn] == 0) 
				valid = true;
		
		return valid;
	
	}
	
	/**
	*Fills the board the knight will be moving on with zeros
	*
	* @param board     A two dimensional array that the knight will move in
	*
	* @return   two dimensional array filled with zero's representing the board the knight will move on
	*/
	public static int[][] fillBoard(int[][] board) {
		
		for (int row = 0; row <= board.length - 1; row++)
			for (int coloumn = 0; coloumn <= board[row].length - 1; coloumn++)
				board[row][coloumn] = 0;
		
		return board;
	}
	
	/**
	*
	*Formats and prints board 
	*
	* @param board     A two dimensional array that the knight will move in
	*
	*/
	public static void printBoard(int[][] board) {
		
		for (int row = 0; row <= board.length - 1; row++)
			for (int coloumn = 0; coloumn <= board[row].length - 1; coloumn++) 
				if (coloumn == board[row].length - 1) {
					System.out.printf("%2d|", board[row][coloumn]); // format the board by adding | to indicate
					System.out.println();										// each square and give the digits in the squares
				}																		// a maximum of two char widths
				else {
					if (coloumn == 0)
						System.out.print("|");
					if (coloumn >= 0)
						System.out.printf("%2d|", board[row][coloumn]);
				}
				
		System.out.println();
		System.out.println();
			
	}
	
}