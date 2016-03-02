import java.util.*;
public class TestDate {
public static void main(String[] args) {
Calendar borrowDate = new GregorianCalendar();
Calendar returnDate = new GregorianCalendar(2007, 24, 11);
String borrow = "";
String returnb = "";
borrow += borrowDate.get(Calendar.MONTH) + "/" + borrowDate.get(Calendar.DATE) + "/" + borrowDate.get(Calendar.YEAR);
returnb += returnDate.get(Calendar.MONTH) + "/" + returnDate.get(Calendar.DATE) + "/" + returnDate.get(Calendar.YEAR);
if (borrow > returnb) 
System.out.print("yes");
else
System.out.print("no");
}
}