public class TestForDoubleIncrement {
public static void main (String[] args) {

for (double monthRate = .05; monthRate <= .08; monthRate = monthRate + .125) {
System.out.println(monthRate);
}
}
}