public abstract class Pedal extends Vehicle {
  
  protected String owner;
  
  public Pedal() {
  }
  
  public String getOwner() {
    return owner;
  }
  
  public void setOwner(String ownerx) {
    this.owner = ownerx;
  }
  
   public int compareToOwner(Pedal p){
    if(this.getOwner().compareTo(p.getOwner()) > 0) {
	   return 1;
	 }
	 if(this.getOwner().compareTo(p.getOwner()) < 0) {
	   return -1;
    }
	 if(this.getOwner() == p.getOwner()) {
	   return 0;
    }
	 else {
	 return 0;
	 }
  }

}