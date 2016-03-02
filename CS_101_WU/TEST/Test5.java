
public class Test5 {
public static void main(String[ ] args) {
String s = new String("Welcome to Java");
Object o = s;
String d = (String)o;
System.out.println(d);
System.out.println(s);
System.out.println(o.toString());
}
}