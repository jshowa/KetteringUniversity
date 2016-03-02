import javax.swing.JOptionPane;

public class Test {
  public static void main(String [] args) {
  String s1 = JOptionPane.showInputDialog("Enter first integer");
  int n1 = Integer.parseInt(s1);
  
  System.out.print(n1);
 }
}