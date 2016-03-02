import java.util.*;

public class TestScanner {
public static void main(String[] args) {
String author = "";
boolean continueLoop = true;

Scanner input = (new Scanner(System.in)).skip("[\\s]+");
System.out.print("Enter the name: ");
author = input.nextLine();


System.out.print(author);

}
}