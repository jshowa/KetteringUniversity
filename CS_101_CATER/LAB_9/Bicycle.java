public class Bicycle extends Pedal {
  
  private int size;
  private int riderNum;
  
  public Bicycle() {
  }
  
  public Bicycle(String makex, int size, int IDNumx, String ownerx, int riderNum) {
    
	 setMake(makex);
	 this.size = size;
	 setIDNum(IDNumx);
	 setOwner(ownerx);
	 this.riderNum = riderNum;
	 
	 
  }
  
  public int getSize() {
    return size;
  }
  
  public int getRiderNum() {
    return riderNum;
  }
  
  public void setSize(int size) {
    this.size = size;
  }
  
  public void setRiderNum(int riderNum) {
    this.riderNum = riderNum;
  }
  
  public String toString() {
    return "b:\t" + getMake() + " " + getSize() + " " + getIDNum() + " " + getOwner() + 
	 			" " + getRiderNum();
  } 
}
  