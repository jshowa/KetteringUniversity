package ArrayADT;

/**
*
*Author: Jacob S. Howarth
*
*Assignment #2
*
*ArrayADT.java
*
*This class defines methods and variables that represent an abstract array collection
*
*/

import java.util.*;

public class ArrayADT<T> {
	
	private final int DEFAULT_CAPACITY = 100;
	private final int NOT_FOUND = -1;
	
	private int count;
	private T[] contents;
	
	
	/**
	*
	*Constructor for the array ADT that creates an array ADT with a default capacity
	*and with element count 0.
	*
	*/
	public ArrayADT() {
		
		count = 0;
		contents = (T[])(new Object[DEFAULT_CAPACITY]);
	
	}
	
	/** 
	*
	*Constructor for an array ADT with an initial capacity specified
	*
	* @param initialCapacity   Specifies an initial size for the array ADT
	*/
	public ArrayADT(int initialCapacity) {
		
		count = 0;
		contents = (T[])(new Object[initialCapacity]);
	
	}
	
	/**
	*
	*The current size of the array ADT (i.e. number of elements in the array ADT)
	*
	* @return   current array ADT size
	*
	*/
	public int size() {
		
		return count;
	
	}
	/**
	*
	*Determines whether the contents of an array ADT is empty or not
	*
	* @return   true if empty, false if not
	*
	*/
	public boolean empty() {
	
		return (count == 0);
	
	}
	
	/**
	*
	*Gets an element in the array at a specific index
	*
	* @param index   index of a position in the array ADT
	*
	* @return   generic element in the given index position
	*
	*/
	public T getElement(int index) {
		
		if (index < 0 || index >= size())
			throw (new IndexOutOfBoundsException());
		else
			return contents[index];
	
	}
	
	/**
	*
	*Adds to the array ADT
	*
	* @param element   A generic element to be added
	*
	*/   
	public void add(T element) {
		
		if (size() == contents.length) 
			extendCapacity();
		else {
			contents[count] = element;
			count++;
		}
	
	}
	
	/**
	*
	*Extends the array ADT size
	*
	*/
	private void extendCapacity() {
		
		T[] larger = (T[])(new Object[contents.length * 2]);
		
		for (int i = 0; i < contents.length; i++) 
			larger[i] = contents[i];
		
		contents = larger;
	
	}
	
	/**
	*
	*Creates and iterator for a specific array ADT instance
	*
	* @return   array ADT iterator
	*
	*/
	public Iterator<T> iterator() {
	
		return new ArrayIterator<T> (contents, count);
		
	}
	
	/**
	*
	*Adds the elements of another array ADT to the current array ADT
	*
	* @param set   the array ADT to be added
	*
	*/
	public void addAll(ArrayADT<T> set) {
	
		Iterator<T> scan = set.iterator();
		
		while (scan.hasNext())
			add(scan.next());
		
	}
	
	/**
	*
	*Searches the array ADT for a specific element
	*
	* @param element   key to search for
	*
	* @return   index of key
	*
	*/
	public int search(T element) {
	
		int item = NOT_FOUND;
		
		for (int i = 0; i < count && item == NOT_FOUND; i++) {
			if (contents[i].equals(element))
				item = i;
		}
		
		return item;
	
	}
	
	/**
	*
	*Converts the elements in the array ADT to String type
	*
	* @return   String containing the array ADT elements
	*
	*/
	public String toString() {
		
		String result = "";
		
		for (int i = 0; i < count; i++)
			result = result + contents[i].toString() + "\n";
		
		return result;
		
	}
	
}
