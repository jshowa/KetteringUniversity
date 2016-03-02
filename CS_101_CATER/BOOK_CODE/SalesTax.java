import java.util.Scanner;

public class SalesTax {
public static void main(String [] args) {
   double purchaseAmount;
	double tax;
	Scanner input = new Scanner(System.in);
	
	System.out.print(" Specify a purchase amount: ");
	purchaseAmount = input.nextDouble();
	
	tax = purchaseAmount * 0.06;
	
	System.out.print((int)(tax * 100) / 100.0);
	
	char letter = '\u0041';
	System.out.print(letter);
	
	char c = (char)0XAB0041;
	System.out.println(c);
	
	char e = 'f';
	System.out.println("\n" + ++e);
	
	int i = 1;
	int j = 2;
	
	System.out.println(" i + j " + i + j);
	
 }
}