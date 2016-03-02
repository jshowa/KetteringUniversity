    public class TriangleJH extends GeometricObject {
      
      private double side1; // Data field side1 contains side 1 of triangles value
      private double side2; // Data field side2 contains side 2 of triangles value
      private double side3; // Data field side3 contains side 3 of triangles value
      private double sumAreaTriangle; // Data field sumAreaTriangle contains the sum of all the triangle objects areas
      private double sumPerimeterTriangle; // Data field sumPerimeterTriangle contains the sum of the perimeter of all the triangle objects
   
   //Constructor 1 -- Used for super class call
       TriangleJH() {
      }
   
   //Constructor 2 -- Creates triangle object with 3 sides
       TriangleJH(double side1, double side2, double side3) {
         if (this.side1 < 0 || this.side2 < 0 || this.side3 < 0) { // if any of the sides are negative, exit program and prompt user to examine file and fix error
            System.out.println("Error: One of the sides of this triangle is negative, please check the file for errors");
            System.exit(0);
         }
         else {
            checkSideConstraint(this.side1, this.side2, this.side3); // check triangle for side constraints
            this.side1 = side1;
            this.side2 = side2;
            this.side3 = side3;
         }
      }
   
   //Method 1 -- Calculate perimeter of a specific triangle object
       public double getPerimeter() {
         return side1 + side2 + side3;
      } // end getPerimeter
   
   //Method 2 -- Calculate area of triangle object using Heron's Formula
       public double getArea() {
         double semiPerimeter = this.getPerimeter() / 2;
         return Math.sqrt(semiPerimeter * (semiPerimeter - side1) * (semiPerimeter - side2) * (semiPerimeter - side3));  
      } // end getArea
   
   //Method 3 -- Checks each triangle objects side constraints to make sure the triangle can be created
       public void checkSideConstraint(double side1, double side2, double side3) {
         boolean sumOfSide1 = side1 + side2 < side3; // check if the sum of two sides are less than the 3rd
         boolean sumOfSide2 = side1 + side3 < side2;
         boolean sumOfSide3 = side3 + side2 < side1;
         if (sumOfSide1 || sumOfSide2 || sumOfSide3) { // if each of the boolean variables are true, there is an error. Prompt user to examine and fix error.
            System.out.println("Error: The sum of two sides of the triangle is less than the third side, please check the file for errors.");
            System.exit(0);
         }
      } // end checkSideConstraint
   
   //Method 8 -- Print a triangle object
       public String toString() {
         return "Triangle --> sides: " + this.side1 + " " + this.side2 + " " + this.side3 + " area: " + this.getArea() + " perimeter: " + this.getPerimeter();
      } // end toString
   
   } // end class