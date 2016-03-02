import java.io.*;
import java.util.*;

public class TestDelimiter {
public static void main(String[] args) throws Exception {

File backupFile = new File("librarybooks.txt");
String[] list;

Scanner input = new Scanner(backupFile);
input.useDelimiter("[, ]");

while (input.hasNext()) {
String s = input.nextLine();
String delims = "[,]";
list = s.split(delims);
for(int i = 0; i <= list.length - 1; i++)
System.out.println(list[i]);
}

System.out.print(Calendar.MONTH);
Scanner inputcheck = new Scanner(System.in);
String response = inputcheck.next();
int error = Integer.parseInt(response);
}
}

