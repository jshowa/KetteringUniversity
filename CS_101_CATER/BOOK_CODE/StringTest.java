import java.util.Scanner;

class StringTest {
  public static void main(String [] args) {
  String dataInput;
  Scanner input;
  String data;
  int data1;
  
  input = new Scanner(System.in);
  
  System.out.print(" Enter an integer: ");
  dataInput = input.next();
  data = input.next(data1);
  
  if (data.equalsIgnoreCase(dataInput) == false) {
  System.out.print(" Enter an integer: ");
  data = input.next();
  }
  
  
  System.out.print(data);
 }
} 