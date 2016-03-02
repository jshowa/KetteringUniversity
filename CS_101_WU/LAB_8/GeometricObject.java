    public abstract class GeometricObject implements Comparable {
    
    // Constructor -- Made explicitly for the super class calls from each sub class constructor
       protected GeometricObject() {
      }
   
    // Method 1 -- abstractly get area of a specific object
       public abstract double getArea();
       
    // Method 2 -- abstractly get perimeter of a specific object
       public abstract double getPerimeter();
       
    // Method 3 -- abstractly print each object
       public abstract String toString();
   
   } // end class