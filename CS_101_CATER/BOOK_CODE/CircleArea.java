/* Jacob Howarth
 * CS-101-07L, Spring 2007
 * Book Examples -- Area of a Circle Calculation
 */
 
 
 /* This program will calculate the area of a circle given a specific radius.
  * All input will be changed within the source code.
  */
  
  
  public class CircleArea {
    public static void main (String [] args) {
	 double radius;  // Declares variable of double data type with "radius" as the identifier
	 double area;  // Declares variable of double data type with identifier "area"
	 int x;  // Variable x
	 int y = 2; // Variable y
	 final double PI = 3.14519
	 
	 // Assign radius a value
	 radius = 20;
	 
	 // Compute area
	 area = radius * radius * PI;
	 
	 // Display results
	 System.out.println(" The area of circle with radius " + radius +
	 							" is " + area );
	 // Compute second area
	 radius = 1.0;
	 x = 1;
	 x = 5 * (3 / 2) + 3 * 2;
	 System.out.println(x);
	 x = y + 1;
	 System.out.println(x);
	 x = x + 1;
	 System.out.println(x); 
	 
	 // New area computation
	 area = radius * radius * PI;
	 
	 System.out.println(" The new area of the circle is " + area );
	 
	 // Compute third area
	 radius = 2.0;
	 
	 // New area computation
	 area = radius * radius * PI;
	 
	 System.out.println(" The newer area of the circle is " + area ); 
  }
}