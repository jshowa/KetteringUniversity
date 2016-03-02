public class Database {
	
	private final static MAX_ENTRY_NUMBER = 100;
	private int currentEntry;
	private Entry[] entries = new Entry[MAX_ENTRY_NUMBER];
	
	public Database() {
		currentEntry = 0;
	}
	
	public void addEntry(Entry entry) {
		
		try {
			entries[currentEntry] = entry;
			currentEntry++;
		}
		catch (Exception e) {
			System.out.println("Error in file. Please check the file to see if it is" +
										"\n empty or has over 100 inputs.");
		}
	}
	
	public  nameLookUp(String name) {
		searchDataBase(this.name);
	}
	
	public void addressLookUp(String address) {
		searchDataBase(this.address);
	}
	
	public void telephoneLookUp(String telephone_number) {
		searchDataBase(this.telephone_number);
	}
	
	public void searchDataBase(String key) {
		
		for (int i = 0; i <= currentEntry - 1; i++) {
			
			if (key.equals(entries[i].getName())) 
				toString(i);
			if (key.equals(entries[i].getAddress()))
				toString(i);
			if (key.equals(entries[i].getTelephoneNumber()))
				toString(i);
		}
	} 
	
		public void toString() {
		for (int i = 0; i <= currentEntry - 1; i++)
			entires[i].toString();
	}
	
	public void toString(int index) {
		entries[i].toString();
	}

}