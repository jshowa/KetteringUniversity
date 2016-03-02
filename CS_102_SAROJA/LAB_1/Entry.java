/**
*Author: Jacob S. Howarth
*Assignment A1
*Entry.java
*
*This class contains methods for an entry in a phone book.
*
*/

public class Entry {
	
	private String name;
	private String address;
	private String telephone_Num;
	
	/**
	*
	*This is the class zero-arg constructor that initializes class instance variables
	*
	*/	
	public Entry() {	
		name = "";
		address = "";
		telephone_Num = "";
	}
	
	/**
	*
	*Overloaded constructor that creates a phone book entry with a specific
	*name, address, and telephone number
	*
	* @param name   person's name in phone book entry
	* @param address    person's address in phone book entry
	* @param telephone_Num   person's telephone number in phone book entry
	*
	*
	*/
	public Entry(String name, String address, String telephone_Num) {
		this.name = name;
		this.address = address;
		this.telephone_Num = telephone_Num;
	}
	
	/**
	*
	*Accessor returns the name of a specific phone book entry instance
	*
	* @return   Name of a person in specific phone book entry instance
	*
	*/
	public String getName() {
		return name;
	}
	
	/**
	*
	*Accessor returns the address of a specific phone book entry instance
	*
	* @return   Address of a person in specific phone book entry instance
	*
	*/
	public String getAddress() {
		return address;
	}
	
	/**
	*
	*Accessor returns the telephone number of a specific phone book entry instance
	*
	* @return   telephone number of a person in specific phone book entry instance
	*
	*/
	public String getTelephoneNumber() {
		return telephone_Num;
	}
	
	/**
	*
	*Mutator for the name of a specific phone book entry instance
	*
	* @param name   Sets a specific name of a person for a phone book entry instance
	*
	*/
	public void setName(String name) {
		this.name = name;
	}
	
   /**
	*
	*Mutator for the address of a specific phone book entry instance
	*
	* @param address   Sets a specific address of a person for a phone book entry instance
	*
	*/
	public void setAddress(String address) {
		this.address = address;
	}
	
   /**
	*
	*Mutator for the telephone number of a specific phone book entry instance
	*
	* @param telephone_Num   Sets a specific telephone number of a person for a phone book 
	*								 entry instance
	*
	*/
	public void setTelephoneNumber(String telephone_Num) {
		this.telephone_Num = telephone_Num;
	}
	
	/**
	*
	*Converts a phone book entry to a string format for printing
	*
	* @return    phone book entry instance converted to a string
	*
	*/
	public String toString() {
		return getName() + ":" + getAddress() + ":" + getTelephoneNumber();
	}
	
}
	