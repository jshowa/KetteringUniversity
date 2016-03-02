/**
*Author: Jacob S. Howarth
*Assignment A1
*Database.java
*
*This class contains methods to search the directory for phone numbers
*by name, address, and telephone number
*
*/

public class Database {
	
	private final static int MAX_ENTRY_NUMBER = 100;
	private int numEntry;
	private int currentIndexOfSearch;
	private Entry[] entries = new Entry[MAX_ENTRY_NUMBER];
	
	/**
	*
	*This is the class zero-arg constructor that initializes class instance variables
	*
	*/	
	public Database() {
		numEntry = 0;
		currentIndexOfSearch = 0;
	}
	
	/**
	*
	*Adds an entry to the given phone directory database instance
	*
	* @param entry   An entry for the phone book
	*
	*/
	public void addEntry(Entry entry) {
		entries[numEntry] = entry;
		numEntry++;
	}
	
	/**
	*
	*Accessor returns current database size for a given database instance
	*
	* @return    An integer representing the database length
	*
	*/
	public int getDatabaseLength() {
		return numEntry;
	}
	
	
	/**
	*
	*Looks up a phone book entry by name in the phone directory database instance
	*
	* @param name   name of the person in the phone directory database to search for
	* 
	* @return   returns all entries in the phone directory database based off a person's name
	*
	*/
	public String nameLookUp(String name) {
		return findAllEntries(name);			
	}
	
	/**
	*
	*Looks up a phone book entry by address in the phone directory database instance
	*
	* @param address   address of the person in the phone directory database to search for
	* 
	* @return   returns all entries in the phone directory database based off a person's address
	*
	*/
	public String addressLookUp(String address) {
		return findAllEntries(address);
	}
	
	/**
	*
	*Looks up a phone book entry by telephone number in the phone directory database instance
	*
	* @param telephone_Num   telephone number of the person in the phone directory database to search for
	* 
	* @return   returns all entries in the phone directory database based off a person's telephone number
	*
	*/
	public String telephoneLookUp(String telephone_Num) {
		return findAllEntries(telephone_Num);
	}
	
	/**
	*
	*Returns a converted entry to string based off the current search index
	*
	* @param index   index of the entry in the phone directory database that matched the searched string from look up methods
	* 
	* @return   returns an entry converted to string that contained a match to the search parameters in the look up methods
	*
	*/
	public String getEntryValueAtCurrentIndex(int index) {
		return entries[index].toString();
	}
	
	/**
	*
	*Finds all entries in the phone directory database that match the parameters in the look up methods 
	*
	* @param key   search parameter string to search the phone directory database for
	* 
	* @return   string concatenation of all entries found that matched the search parameter key
	*
	*/
	public String findAllEntries(String key) { // key resembles the string to find
		
		int indexOfEntry; // the indexOfEntry holds the entry that matches the key in the searchDataBase method
		String entryValues = "";
		
		while (currentIndexOfSearch <= numEntry - 1) {
			indexOfEntry = searchDataBase(key); // search the entries array up to number of entries
				
				if (indexOfEntry < 0) // if not found return null string
					return entryValues;
				else {
					entryValues += getEntryValueAtCurrentIndex(indexOfEntry) + "\n"; // if found concatenate and ...
					currentIndexOfSearch = indexOfEntry + 1; // ... start the search at a position one more then indexOfCurrentSearch
				}
				
		}
		
		currentIndexOfSearch = 0;
		return entryValues;
	}
	
   /**
	*
	*This method performs the actual search for entries as a helper method to the findAllEntries method.
	*It performs a linear search comparing all entries to the search parameter by name, address, and telephone
	*number and returns the index of the entry if equal.
	*
	* @param key   search parameter string in the phone directory database to search for
	*
	* @return   returns the index of the entry in the phone directory database based off a person's address, name, or phone number
	*
	*/
	public int searchDataBase(String key) {
		
		int resultIndex = -1;
		
		// This is a linear search algorithm O(n^2)
		for (int i = currentIndexOfSearch; i <= numEntry - 1; i++) {	
			
			if (key.equals(entries[i].getName())) // compare Entry name to key and see if equal 
				return resultIndex = i;
			
			if (key.equals(entries[i].getAddress())) // compare Entry address to key and see if equal
				return resultIndex = i;
			
			if (key.equals(entries[i].getTelephoneNumber())) // compare Entry telephone number to key and see if equal
				return resultIndex = i;
			 
		}
		
		if (resultIndex < 0)
			return resultIndex = -1; // otherwise return negative one if search did not find a matching entry
		
		return resultIndex;
	}
	
	/**
	*
	*Converts all entries in the phone directory database to a string equivalent
	*
	* @return   all entries in the phone directory database concatenated together
	*
	*/ 
	public String toString() { 
		
		String dataBaseOutput = "";
		
		for (int i = 0; i <= numEntry - 1; i++)
			dataBaseOutput += entries[i].toString() + "\n";
		
		return dataBaseOutput;
	}
	
}