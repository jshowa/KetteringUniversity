import java.util.*;

class TestStringDelimArray {
public static void main(String[] args) {
Scanner input = new Scanner(System.in);
String x = "";
String y = "";

x = input.next();

String list[];
String delims = "[/]";

list = x.split(delims);

for (int i = 0; i <= list.length - 1; i++) 
list[i] = list[i].trim();

for (int i = 0; i <= list.length - 1; i++) 
y += list[i];

System.out.print(y);

}
}