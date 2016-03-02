    public class RectangleJH extends GeometricObject {
   
      private double length; //Data field length contains length value of rectangle object
      private double width; //Data field width contains width value of rectangle object
      private double sumAreaRectangle; //Data field contains the sum of all the areas of the rectangle objects
      private double sumPerimeterRectangle; //Data field contains the sum of all the perimeters of the rectangle objects
   
   //Constructor 1 -- Used for super class calls
       RectangleJH() {
      }
   
   //Constructor 2 -- Creates Rectangle object with a length and width
       RectangleJH(double length, double width) {
         if (this.length < 0 || this.width < 0) { // if the length or width of the rectangle is negative, exit program and prompt user to fix error
            System.out.println("Error: one of the rectangle sides are negative, please check the file for errors.");
            System.exit(0);
         }
         else {
            this.length = length;
            this.width = width;
         }
      }
   
   //Method 1 -- Calculates area of a rectangle object
       public double getArea() {
         return length * width;
      }
   
   //Method 2 -- Calculates perimeter of a rectangle object
       public double getPerimeter() {
         return (2 * length) + (2 * width);
      }
   
   //Method 3 -- Prints a rectangle object
       public String toString() {
         return "Rectangle --> length: " + this.length + " width: " + this.width + " area: " + this.getArea() + " perimeter: " + this.getPerimeter();
      }
   
   } // end class