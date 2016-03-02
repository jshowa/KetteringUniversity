/**
*
*Author: Jacob Howarth
*Assignment: A3
*
*LinkListIterator.java
*
*This class contains an iterator to travel the link list
*
*/
import java.util.*;

public class LinkListIterator<T> implements Iterator<T> {
	
	private int count;
	private LinearNode<T> current;
	
	
	/**
	*
	*Constructor for creating the linked list iterator. The constructor sets the
	*current size and head of the linked list
	*
	* @param head   head node of the current linked list
	* @param size   current size of the link list
	*
	*/
	public LinkListIterator(LinearNode<T> head, int size) {
		
		current = head;
		count = size;
	
	}
	
	/**
	*
	*Checks to see if the current node has a null address
	*
	* @return   true if the iterator reached the end of the link list, false otherwise
	*
	*/
	public boolean hasNext() {
		
		return (current != null);
	
	}
	
	/**
	*
	*Returns the element of the current node and advances the current node to the 
	*next available node
	*
	* @return   generic type data element stored in node
	*
	*/
	public T next() {
		
		if (!hasNext()) 	
			throw new NoSuchElementException();
		
		T result = current.getElement();
		current = current.getNext();
		
		return result;
	
	}
	
	/**
	*
	*Removes and element, however this operation is not supported
	*
	* @return  void
	*
	*/
	public void remove() throws UnsupportedOperationException {
		
		throw new UnsupportedOperationException();
	
	}

}
		
		
	