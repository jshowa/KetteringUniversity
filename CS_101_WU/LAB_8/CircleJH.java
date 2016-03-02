    public class CircleJH extends GeometricObject {
      
      private double radius; // radius = radius of circle object
      private double sumAreaCircle; // sumAreaCircle = static variable that holds the sum of the areas of all circle objects
      private double sumPerimeterCircle; // sumPerimeterCircle = static variable that holds the sum of the perimeters of all circle objects
   
   // Constructor 1 -- Creates a circle object with default radius 0;
       CircleJH() {
      }
   
   // Constructor 2 -- Creates a circle object with a specified radius, if radius is negative it is assigned the default 0.
       CircleJH(double radius) {
         if (this.radius < 0) { // if radius is negative, exit program and prompt user to examine and fix error in file
            System.out.println("Error: The radius of the circle is negative, please check the file for errors.");
            System.exit(0);
         }
         else {
            this.radius = radius;
         }
      }
   
   // Method 1 -- Accessor method that returns the radius of the current circle object
       public double getRadius() {
         return radius;
      } // end getRadius
   
   // Method 2 -- Accessor method that returns area of the current circle object
       public double getArea() {
         return radius * radius * Math.PI;
      } // end getArea
   
   // Method 3 -- Accessor method that returns circumference of the current circle object
       public double getPerimeter() {
         return 2 * Math.PI * radius;
      } // end getPerimeter
   
   // Method 4 -- Print the circle object
       public String toString() {
         return "Circle: --> radius: " + this.getRadius() + " area: " + this.getArea() + " circumference: " + this.getPerimeter(); 
      } // end toString
   
   } // end class