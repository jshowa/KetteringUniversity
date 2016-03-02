package ArrayADT;

/**
*
*Author: Jacob S. Howarth
*
*Assignment #2
*
*ArrayIterator.java
*
*This class defines an iterator for an array ADT
*
*/

import java.util.*;

public class ArrayIterator<T> implements Iterator<T> {
	
	private int count;
	private int current;
	private T[] items;
	
	/**
	*
	*Constructor that initializes an iterator for a specifc array ADT
	*
	* @param collection   array ADT with elements of generic type
	* @param size   the current size of the array ADT
	*
	*/
	public ArrayIterator(T[] collection, int size) {
		
		items = collection;
		count = size;
		current = 0;
		
	}
	
	/**
	*
	*Determines whether the array ADT has another element stored in the iteration
	*
	* @return   true if array has another element, false if no other element 
	*
	*/
	public boolean hasNext() {
		
		return (current < count);
	
	}
	
	/**
	*
	*Returns the next element in the iteration of the array ADT if it exists
	*
	* @return   generic element stored in the array ADT 
	*
	*/ 
	public T next() {
		
		if (!hasNext())
			throw new NoSuchElementException();
			
		current++;
		
		return items[current - 1];
	
	}
	
	/**
	*
	*Abstract method in Iterator interface but not supported by this ADT
	*
	*/
	public void remove() throws UnsupportedOperationException {
		
		throw new UnsupportedOperationException();
		
	}
}