import java.util.*;

public class TestMatches {
public static void main(String[] args) {
Scanner input = new Scanner(System.in);
String response = "";

System.out.print("Enter something to match: ");
response = input.next();
System.out.print(response.matches("[\\d]+[\\W]*[.][\\d]{2}"));

}
}