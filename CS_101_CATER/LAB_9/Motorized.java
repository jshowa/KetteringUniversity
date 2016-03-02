public abstract class Motorized extends Vehicle {
  
  protected String licensePlateNum;
  protected int manYear;
  
  public Motorized() {
  }
  
  public String getLicensePlateNum() {
    return licensePlateNum;
  }
  
  public int getManYear() {
    return manYear;
  }
  
  public void setLicensePlateNum(String licensePlateNumx) {
    this.licensePlateNum = licensePlateNumx;
  }
  
  public void setManYear(int manYearx) {
    this.manYear = manYearx;
  }
}
  
  