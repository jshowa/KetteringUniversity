public class Entry {
	
	private String name;
	private String address;
	private String telephone_number;
	
	public Entry() {
		
		name = "";
		address = "";
		telephone_number = "";
		
	}
	
	public Entry(String name, String address, String telephone_number) {
		this.name = name;
		this.address = address;
		this.telephone_number = telephone_number;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getTelephoneNumber() {
		return telephone_number;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setTelephoneNumber(String telephone_number) {
		this.telephone_number = telephone_number;
	}
	
	public String toString() {
		return getName() + ":" + getAddress() + ":" + getTelephoneNumber();
	}
	
}
	