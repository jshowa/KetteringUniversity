import java.util.*;
public class Test12 {
	  public static void main(String[] args) { 
	    Count myCount = new Count(); 
	    int times = 0; 

	    for (int i = 0; i < 10; i++) 
	      increment(myCount, times); 

	    System.out.println( 
	      "myCount.count =   " + myCount.count); 
	    System.out.println("times = " + times); 
	  } 

	  public static void increment(Count c, int times) { 
	    c.count++; 
	    times++; 
	  } 
	} 

	class Count { 
	  int count; 

	  Count(int c) { 
	    count = c; 
	  } 

	  Count() { 
	    count = 1; 
	  } 
	}
