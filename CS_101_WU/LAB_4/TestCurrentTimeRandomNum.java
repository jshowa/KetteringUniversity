import java.util.Random;

public class TestCurrentTimeRandomNum {
public static void main(String[] args) {

double n = 0;
double end = 1000;
double start = 0;


for (int i = 1; i <= 10; i++) {
n = (Math.random() * (end - start + 1) + start);
System.out.println(n);
}

}
}
