public abstract class User {

protected User() { // user 0 arg constructor called by the subclasses with constructor chaining
}

public abstract int getUpperLimit(); // abstract method to get the different maximum borrow values of a student or faculty object

} // end User class