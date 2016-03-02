/**
*
*Author: Jacob Howarth
*Assignment: A3
*
*LinearNode.java
*
*This class contains methods and data for a node in a linked list
*
*/

public class LinearNode<T> {

	private LinearNode<T> next;
	private T element;
	
	/**
	*
	*Zero argument constructor for a node with a null address and no data
	*
	*/
	public LinearNode() {
		
		next = null;
		element = null;
	
	}
	
	/**
	*
	*Constructor that creates a node with given data to store
	*
	* @param element   generic type data element for node to store
	*
	*/
	public LinearNode(T element) {
		
		next = null;
		this.element = element;
	
	}
	
	/**
	*
	*Gets the pointer/address the current node is pointing too
	*
	* @return   linear node of generic type that the current node points too
	*
	*/
	public LinearNode<T> getNext() {
		
		return next;
	
	}
	
	/**
	*
	*Set's the current node to point to another node
	*
	* @param node   the node that the current nodes next pointer will be pointing to
	*
	* @return  void
	*
	*/
	public void setNext(LinearNode<T> node) {
		
		next = node;
		
	}
	
	/**
	*
	*Retrieve the data element the current node is storing
	*
	* @return   an object of generic type
	*
	*/
	public T getElement() {
		
		return element;
	
	}
	
	/**
	*
	*Set's the data element of the current node to a specifc value
	*
	* @param element   data of generic type to be stored in the node
	*
	* @return   void
	*
	*/
	public void setElement (T element) {
		
		this.element = element;
	
	}
	
	/**
	*
	*Checks if two nodes are equal by comparing their data elements
	*
	* @param node   linear node of generic type to be compared
	*
	* @return   true if both nodes have equal data elements, false otherwise
	*
	*/
	public boolean equals(LinearNode<T> node) {	
		
		boolean result = true; 

		if (this.getElement() == node.getElement())
			result = true;
		else
			result = false;
		
		return result;
	
	}
	
}