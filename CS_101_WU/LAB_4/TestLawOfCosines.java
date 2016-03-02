public class TestLawOfCosines {
public static void main(String[] args) {

double side1 = 5;
double side2 = 5;
double side3 = 6;
double part1 = 0;
double angle = 0;

part1 = Math.pow(side1, 2) + Math.pow(side2, 2) - Math.pow(side3, 2);
angle = Math.acos(part1 / (side1 * side2));

System.out.print(angle);
}
}