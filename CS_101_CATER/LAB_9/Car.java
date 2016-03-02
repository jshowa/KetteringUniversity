public class Car extends Motorized {

  private int doorNum;
  
  public Car() {
  }
  
  public Car(String makex, int doorNum, int IDNumx, String licensePlateNumx, int manYearx) {
  
    setMake(makex);
	 this.doorNum = doorNum;
	 setIDNum(IDNumx);
	 setLicensePlateNum(licensePlateNumx);
	 setManYear(manYearx);

  }
  
  public int getDoorNum() {
    return doorNum;
  }
  
  public void setDoorNum(int doorNum) {
    this.doorNum = doorNum;
  }
  
  public int compareToDoorNum(Car c) {
    if(this.getDoorNum() > c.getDoorNum()) {
	   return 1;
	 }
	 if(this.getDoorNum() < c.getDoorNum()) {
	   return -1;
	 }
	 if(this.getDoorNum() == c.getDoorNum()) {
	   return 0;
	 }
	 else {
	   return 0;
    }
  }
  
  public String toString() {
    return "c:\t" + getMake() + " " + doorNum + " " + getIDNum() + " " +
	        getLicensePlateNum() + " " + getManYear();
  }		  
}
	  