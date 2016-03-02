import java.util.Scanner;

class InputOneLineTest {
  public static void main (String [] args) {
  
  double monthDay;
  int monthName;
  int year;
  double min1;
  double hour1;
  String era;
  
  Scanner input = new Scanner(System.in);
  
  System.out.print(" Enter first date: " + "/n" + "\t" + (era = input.next())
 + "\t" + (year = Integer.parseInt(input.next()))
 + "\t" + (monthName = Integer.parseInt(input.next())) + "\t" + (monthDay = Integer.parseInt(input.next())) 
 + "\t ");
   }
  }