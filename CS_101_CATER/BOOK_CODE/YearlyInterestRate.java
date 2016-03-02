import java.util.Scanner;

class YearlyInterestRate {
public static void main(String [] args) {
   Scanner input;
	String yearlyInterestRate;
	
	input = new Scanner(System.in);
	
	System.out.print(" Enter yearly interest rate: ");
	yearlyInterestRate = input.next();
	int annualInterestRate = Integer.parseInt(yearlyInterestRate);
	 
	System.out.print(yearlyInterestRate);
 }
}

	 