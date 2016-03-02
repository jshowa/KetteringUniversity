public class Circle9 extends GeometricObject1 {
/** Default constructor */
public Circle9( ) {
this(1.0);
System.out.print("C");
}

/** Construct circle with a specified radius */
public Circle9(double radius) {
this(radius, "white", false);
System.out.print("D");
}

/** Construct a circle with specified radius, filled, and color */
public Circle9(double radius, String color, boolean filled) {
super(color, filled);
System.out.print("E");
}
}