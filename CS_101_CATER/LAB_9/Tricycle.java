public class Tricycle extends Pedal {

  private String color;
  private int maxAge;
  
  public Tricycle() {
  }
  
  public Tricycle(String makex, String color, int IDNumx, String ownerx, int maxAge) {
  
    setMake(makex);
	 this.color = color;
	 setIDNum(IDNumx);
	 setOwner(ownerx);
	 this.maxAge = maxAge;
	 
  }
  
  public String getColor() {
    return color;
  }
  
  public int getMaxAge() {
    return maxAge;
  }
  
  public void setColor(String color) {
    this.color = color;
  }
  
  public void setMaxAge(int maxAge) {
    this.maxAge = maxAge;
  }
  
  public String toString() {
    return "t:\t" + getMake() + " " + getColor() + " " + getIDNum() + " " + getOwner() +
	        " " + getMaxAge();
  }
}