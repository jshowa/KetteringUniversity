public abstract class Vehicle extends Object {
  
  protected String make;
  protected int IDNum;
  
  public String getMake() {
    return make;
  }
  
  public int getIDNum() {
    return IDNum;
  }
  
  public void setMake(String makex) {
    this.make = makex;
  }
  
  public void setIDNum(int IDNumx) {
    this.IDNum = IDNumx;
  }
  
  public int compareToID(Vehicle v){
    if(this.getIDNum() > v.getIDNum()) {
	   return 1;
	 }
	 if(this.getIDNum() < v.getIDNum()) {
	   return -1;
    }
	 if(this.getIDNum() == v.getIDNum()) {
	   return 0;
    }
	 else {
	 return 0;
	 }
  }
  
  public int compareToMake(Vehicle v1) {
    if (this.getMake().compareTo(v1.getMake()) > 0) {
	   return 1;
    }
	 if (this.getMake().compareTo(v1.getMake()) < 0) {
	   return -1;
	 }
	 if (this.getMake() == v1.getMake()) {
	   return 0;
	 }
	 else {
	   return 0;
	 }
  }
}