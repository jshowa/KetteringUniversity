/**
*
*Author: Jacob Howarth
*Assignment: A3
*
*LinkedList.java
*
*This class contains methods and data for building and performing operations
*on Linked Lists
*
*/
import java.util.*;

public class LinkedList<T> {
	
	private int count;
	private LinearNode<T> head;
	private LinearNode<T> tail;
	
	
	/**
	*
	*Constructor that creates an empty link list
	*
	*/
	public LinkedList() {
		head = null;
		tail = null;
		count = 0;
	}
	
	/**
	*
	*Add's an element to the link list at a specific index
	*
	* @param elem   data element of generic type for node
	* @param index  position to insert the element
	*
	*/
	public void add(T elem, int index) throws IndexOutOfBoundsException {
		
		if (index < 0 || index > size()) 	
			throw (new IndexOutOfBoundsException());
		else {
			if (index == 1)
				addFirst(elem);
			else if (index == (size() + 1)) 
				addLast(elem);
			else {
				
				LinearNode<T> insert = new LinearNode<T>(elem);
				LinearNode<T> current = head, prev = head;
				
				for (int i = 1; i < index; i++) {
					prev = current;
					current = current.getNext();
				}
				
				insert.setNext(current);
				prev.setNext(insert);
				count++;
			
			}
		}
	}
	
	/**
	*
	*Add's an element to the beginning of the link list
	*
	* @param elem   data element of generic type for added node
	*
	*/
	public void addFirst(T elem) {
		
		LinearNode<T> toAdd = new LinearNode<T>(elem);
		
		if (head == null) {
			head = toAdd;
			tail = toAdd;
		}
		else {
			toAdd.setNext(head);
			head = toAdd;
		}
		
		count++;
	
	}
	
	/**
	*
	*Add's an element to the end of the link list
	*
	* @param elem   data element of generic type for added node
	*
	*/
	public void addLast(T elem) {
		
		LinearNode<T> addLast = new LinearNode<T>(elem);
		
		if (head == null) {
			head = addLast;
			tail = addLast;
		}
		else {
			tail.setNext(addLast);
			tail = addLast;
		}
		
		count++;
		
	}
	
	/**
	*
	*Accessor method that retrieves and element at a given index from the
	*link list
	*
	* @param index   position of element in link list 
	*
	* @return   data element of generic type residing at the index
	*
	*/
	public T get(int index) throws IndexOutOfBoundsException {
		
		T result;
		LinearNode<T> current;
		
		if (index < 0 || index > size()) 
			throw (new IndexOutOfBoundsException());
		else {
		
			current = head;
		
			for (int i = 1; i < index; i++) {
				current = current.getNext();
			}
		
			result = current.getElement();
		}
		
		return result;
	}
	
	/**
	*
	*Accesses the element at the front of the link list
	*
	* @return   data element of generic type at the beginning of the link list
	*
	*/
	public T getFirst() throws NoSuchElementException {
		
		if(empty()) 
			throw (new NoSuchElementException());
		
		return head.getElement();
	
	}
	
	/**
	*
	*Accesses the element at the end of the link list
	*
	* @return   data element of generic type at the end of the link list
	*
	*/
	public T getLast() throws NoSuchElementException {
		
		if(empty()) 
			throw new NoSuchElementException();
		
		return tail.getElement();
	
	}
	
	/**
	*
	*Remove's an element from the link list at a given index and returns that element
	*
	* @param index   position of element in link list to be removed
	*
	* @return   data element of generic type to be removed from the list
	*
	*/
	public T remove(int index) throws IndexOutOfBoundsException {
		
		T result = null;
		
		if (index < 0 || index > size()) 
			throw (new IndexOutOfBoundsException());
		else {
			
			if (index != 1) {
				
				LinearNode<T> prev = head, current = head;
				
				for (int count = 1; count < index; count++) {
					prev = current;
					current = current.getNext();
				}
				
				if (tail == current) // if the last element is to be removed, the tail must be reset
					tail = prev;		// so if the current reference equals the tail reference set the tail
											// reference to the prev node reference because the current node is the
											// node being deleted
				
				result = current.getElement();
				prev.setNext(current.getNext());
				current.setNext(null); // completely deletes the current node from the list
				count--;
			}
			else 
				result = removeFirst();
		}
		
		return result;
	}
	
	/** 
	*
	*Searches the list and removes the first occurence of the element
	*
	* @param elem   data element of generic type to be removed from the list
	*
	* @return   true if element is removed, false otherwise
	*
	*/
	public boolean remove (T elem) {
		
		int indexOfRemoval;
		boolean removable = false;
		T result = null;
		
		indexOfRemoval = this.contains(elem);
		
		if (indexOfRemoval > 0) {
			removable = true;
			result = remove(indexOfRemoval);
		}
		
		return removable;
	}
	
	/**
	*
	*Removes and returns an element from the beginning of the link list
	*
	* @return   data element of generic type to be removed from the list
	*
	*/ 
	public T removeFirst() {
		
		T value = null;
		
		if (!empty()) {
			value = head.getElement();
			head = head.getNext();
			count--;
		}
		
		if (empty()) // return null if the list is empty
			value = null;

		return value;
		
	}
	
	/**
	*
	*Removes and returns an element from the end of the link list
	*
	* @return   data element of generic type to be removed from the list
	*
	*/ 
	public T removeLast() {
	
		T result = null;
		LinearNode<T> current;
		LinearNode<T> previous;
		
		if (!empty()) {
			
			result = tail.getElement();
			
			current = head;
			
			while (current.getNext() != tail) // link list must be traversed so tail can be 
				current = current.getNext();	// re-assigned to the previous node after the 
														// tail node gets removed
			
			previous = current;
			tail = previous; // set tail reference to point to previous node reference
			tail.setNext(null); // set new tail reference's next pointer to null, deleting the old tail
		
		}
		
		if (empty())
			result = null;
		
		count--; // reduce list size
		return result; 
	}
	
	/**
	*
	*Searches the link list for a given key and returns the index of the key
	*
	* @param elem   key to search for in link list
	*
	* @return   index for the position of the key in the link list if it exists, -1 if not found
	*
	*/
	public int contains(T elem) {
		
		Iterator<T> linkList = this.iterator();
		boolean found = false;
		int count = 1;
			
		while (linkList.hasNext() && !found) {
			
			T currentElem = linkList.next();
			
			if (currentElem.equals(elem)) 
				found = true;
				
			count++;
		
		}
		
		count--; // because the while loop counts one extra if the first element of the linked list starts at 1
		
		if (!found)
			count = -1; // return -1 if not found
			
		return count;
	}
	
	/**
	*
	*Creates a link list iterator for the current link list used to traverse the link list
	*
	* @return   iterator of generic type
	*
	*/
	public Iterator<T> iterator() {
		
		return new LinkListIterator<T>(head, count);
	
	}
	
	/**
	*
	*Current number of elements in the link list
	*
	* @return   number of elements
	*
	*/
	public int size() {
		
		return count;
		
	}
	
	/**
	*
	*Checks to see if the link list is an empty list
	*
	* @return   true if list has no elements, false otherwise
	*
	*/
	public boolean empty() {
		
		return (head == null);
	
	}
	
	/**
	*
	*Displays all elements within the link list
	*
	* @return   a string representing the elements in the list
	*
	*/
	public String toString() {
		
		String result = "";
		
		if (!empty()) {
			
			Iterator<T> linkList = this.iterator();
			
			while (linkList.hasNext()) {
				
				T currentElem = linkList.next();
				
				if (currentElem != null) 
					result = result + currentElem.toString() + " ";
			}
			
		}
		
		return result;
	}
}