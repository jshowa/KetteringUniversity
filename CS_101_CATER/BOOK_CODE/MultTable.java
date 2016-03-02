import java.util.Scanner;

class MultTable {
  public static void main (String[] args) {
  int a;
  int b;
  int i;
  int j;
  
  Scanner input = new Scanner(System.in);
  
  System.out.print(" Enter the first integer: ");
    
	 a = input.nextInt();
	 
	 System.out.print(" Enter the second integer: ");
	 
	 b = input.nextInt();
	 
	 if ( b <= a )
	 
	 System.out.print("Bad input.");

  while ( b <= a) {
  
  System.out.print(" Enter the first integer: ");
    
	 a = input.nextInt();
	 
	 System.out.print(" Enter the second integer: ");
	 
	 b = input.nextInt();
	 
	 if ( b <= a )
	 
	 System.out.print("Bad input.");
	 	 
	 } 
	 
	 System.out.print("% java SumSquares " + a + " " + b + "\n" + a + " " + b + "\n" + sumSquares(a , b));
	
	 System.out.println();
	 
  for(i = 0 ; i < 10 ; i++) {
    for( j = 0 ; j < 10 ; j++) {
	   
		System.out.print(i * j + " ");
		
	 }
  
  System.out.println();
  
  }
  
  } // method close
  
  public static int sumSquares(int a , int b) {
  
  int sum = 0;
    	 
	 if (b <= a) {
	 
	   System.out.print(" Bad Input. ");
		return sum;
	 }			
  
  for (int i = a ; i <= b ; i++) {
  
  sum += Math.pow(i, 2);
  
  } // close for
  
  return sum;
   
 } // close method
 } // close class
   