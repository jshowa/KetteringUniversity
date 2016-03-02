public class FahrenheitToCelsius {
   public static void main(String [] args) {
	  double farenheit = 100;
	  double celsius = (5.0 / 9) - (farenheit - 32);
	  
	  System.out.println(" Fahrenheit " + farenheit + " is " + celsius + " in Celsius");
	  
	  int i = 10;
	  int newNum = 10 * i++;
	  
	  System.out.println(newNum);
	  
	  int newNum2 = 10 * (++i);
	  
	  System.out.println(newNum2);
  }
}