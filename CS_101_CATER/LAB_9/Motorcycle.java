public class Motorcycle extends Motorized {

  private String color;
  
  public Motorcycle() {
  }
  
  public Motorcycle(String makex, String color, int IDNumx, String licensePlateNumx, int manYearx) {
	 
	 setMake(makex);
	 this.color = color;
	 setIDNum(IDNumx);
	 setLicensePlateNum(licensePlateNumx);
	 setManYear(manYearx);
	
  }
  
  public String getColor() {
    return color;
  }
  
  public void setColor(String color) {
    this.color = color;
  }
  
  public String toString() {
    return "m:\t" + getMake() + " " + color + " " + getIDNum() +
	 " " + getLicensePlateNum() + " " + getManYear();
}
}