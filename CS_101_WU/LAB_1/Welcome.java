/* Jacob S. Howarth
 * CS-101, 10/10/07
 * Changua Wu Fall 2007
 */
 
 /* This application program prints Welcome to Java and converts two numbers entered
  * as strings into numbers and sums them.
  */
  
  import javax.swing.JOptionPane;
  
  public class Welcome {
    public static void main(String[] args) {
	   
	   // Print the statement welcome to java to the console window.
	   System.out.println("Welcome to Java");
	   System.out.println("This program will return the sum of two numbers!");
	   
	   // Open the dialog box and prompt user to enter two numbers.
	   String num1 = JOptionPane.showInputDialog("Please enter the first number");
	   String num2 = JOptionPane.showInputDialog("Please enter the second number");
	   
	   // Convert the string type numbers to integers and store them as ints.
	   int stringNum1 = Integer.parseInt(num1);
	   int stringNum2 = Integer.parseInt(num2);
		
	   // compute the sum
	   int sum = 0;
		sum = stringNum1 + stringNum2;
		
	   // print sum to console output.
	   System.out.println("The sum of " + stringNum1 + " and " + stringNum2 + " is " + sum);
	} // end main
  } // end class