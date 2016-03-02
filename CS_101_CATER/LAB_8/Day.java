/* Jacob S. Howarth
 * CS-101-07L, Spring 2007
 * Lab 7 -- Day Class
 */
 
 /* This class creates four day objects each initialized at different days, months and years. 
  * The class then performs calculations on each of the constructors that can do them, such 
  * as calculating the number of days between the date, adding a number of days, and finding
  * the previous and next days. It also calculates the specific day of week each constructor
  * is on. The list of methods accessible by the user are as follows:
  *
  * getYear()
  * getMonth()
  * getDay()
  * getDayofWeek()
  * setYear()
  * setMonth()
  * setDay()
  * nextDay()
  * previousDay()
  * daysBetween()
  * addDays()
  * before()
  * after()
  * equals()
  * compareTo()
  * classId()
  * toString()
  *
  */

public class Day {

  private int day; // global day variable for object instance
  private int month; // global month variable for object instance
  private int year; // global year variable for object instance
  private final static int EPOCH = 2440588; /* constant that calculates the Julian Date for
  															* Jan. 1, 1970. This variable is accessible to 
															* all objects and is required in calculations 
															  for each instance. */
	
															
  // Zero argument constructor that intializes to the current day   
  public Day() {
  
    JDCalcToday();
	 
  }
 
  
  // Constructor that intializes to January first of a given year 
  public Day(int year) {
  
    this.month = 1;
	 this.day = 1;
	 this.year = year;
	 
  }
 
  
  // Constructor that intializes to the first day of a given month and year
  public Day(int year, int month) {
  
    this.year = year;
	 this.month = month;
	 this.day = 1;
	 
  }
 
  
  // Constructor that intializes to a given month, day, and year
  public Day(int year, int month, int day) {
  
    this.year = year;
	 this.month = month;
	 this.day = day;
  
  }
 
  
  // Constructor that makes a copy of a given Day
  public Day(Day d) {
  
    this.day = d.getDay();
    this.month = d.getMonth();
    this.year = d.getYear();

  }
 
  
  // Returns the year of the instance
  public int getYear() {
    
	 return this.year;
  
  }
 
  
  // Returns the month of the instance
  public int getMonth() {
    
	 return this.month;
  
  }
 
  
  // Returns the day of the instance
  public int getDay() {
    
	 return this.day;
  
  }
 
  
  // Returns the day of the week for a given instance
  public DayName getDayOfWeek() {
    
	 switch ((JDinstance() + 1) % 7) {
	   case 0: return DayName.SUN;
		case 1: return DayName.MON;
		case 2: return DayName.TUE;
		case 3: return DayName.WED;
		case 4: return DayName.THU;
		case 5: return DayName.FRI;
	   default: return DayName.SAT;
	 }
  
  }
 
  
  // Sets the year for an instance given a year
  public void setYear(int year) {
    
	 this.year = year;
  
  }
 
  
  // Sets the month for an instance given a month
  public void setMonth(int month) {
    
	 this.month = month;
  
  }
 
  
  // Sets the day for an instance given a day
  public void setDay(int day) {
    
	 this.day = day;
  
  }
 
  
  // Takes an instance and adds one day to it
  public void nextDay() {
    
	 int JD; // claculates Julian Date + 1 day for each Day instance that calls it
	 int a; // calculation variable used to recalculate the instances new day, month and year
	 int b; // calculation variable used to recalculate the instances new day, month and year
	 int c; // calculation variable used to recalculate the instances new day, month and year
	 int d; // calculation variable used to recalculate the instances new day, month and year
	 int e; // calculation variable used to recalculate the instances new day, month and year
	 int m; // calculation variable used to recalculate the instances new day, month and year
	  
	 JD = JDinstance() + 1;
	 
	 a = JD + 32044;
	 b = (4 * a + 3) / 146097;
	 c = a - (146097 * b) / 4;
	 
	 d = (4 * c + 3) / 1461;
	 e = c - (1461 * d) / 4;
	 m = (5 * e + 2) / 153;
	 
	 this.day = e - (153 * m + 2) / 5 + 1;
	 this.month = m + 3 - 12 * (m / 10);
	 this.year = 100 * b + d - 4800 + m / 10;
	 
  }
 
  
  // Takes an instance and subtracts a day from it
  public void previousDay() {
    
	 int JD; // calculates Julian Date - 1 for each Day instance that calls it
	 int a; // calculation variable used to recalculate the instances new day, month and year
	 int b; // calculation variable used to recalculate the instances new day, month and year
	 int c; // calculation variable used to recalculate the instances new day, month and year
	 int d; // calculation variable used to recalculate the instances new day, month and year
	 int e; // calculation variable used to recalculate the instances new day, month and year
	 int m; // calculation variable used to recalculate the instances new day, month and year
	 
	 JD = JDinstance() - 1;
	 
	 a = JD + 32044;
	 b = (4 * a + 3) / 146097;
	 c = a - (146097 * b) / 4;
	 
	 d = (4 * c + 3) / 1461;
	 e = c - (1461 * d) / 4;
	 m = (5 * e + 2) / 153;
	 
	 this.day = e - (153 * m + 2) / 5 + 1;
	 this.month = m + 3 - 12 * (m / 10);
	 this.year = 100 * b + d - 4800 + m / 10;
	 
  }
 
  
  // Finds the days between an instance and the argument
  public int daysBetween(Day d) {
    
	 int JD; // Julian Date for the day instance
	 int JDd; // Julian Date for the argument day instance
	 int dayDifference; // the total number of days between the instance and the argument
  
    JD = JDinstance();
    JDd = JDargument(d);
  
    dayDifference = JD - JDd;
  
    return dayDifference;
	 
  }
 
  
  // Adds a certain number of days to an instance and returns a new instance of a Day
  public Day addDays(int numberOfDays) {
    
	 int JD; // calculates old instances Julian Date and adds an int number of days
	 int a; // calculation variable used to recalculate the instances new day, month and year
	 int b; // calculation variable used to recalculate the instances new day, month and year
	 int c; // calculation variable used to recalculate the instances new day, month and year
    int d; // calculation variable used to recalculate the instances new day, month and year
    int e; // calculation variable used to recalculate the instances new day, month and year
    int m; // calculation variable used to recalculate the instances new day, month and year
	 int day; // new instances day calculation
	 int month; // new instances month calculation
	 int year; // new instances year calculation
	 
	 JD = JDinstance() + numberOfDays;
	 
	 a = JD + 32044;
	 b = (4 * a + 3) / 146097;
	 c = a - (146097 * b) / 4;
	 
	 d = (4 * c + 3) / 1461;
	 e = c - (1461 * d) / 4;
	 m = (5 * e + 2) / 153;
	 
	 day = e - (153 * m + 2) / 5 + 1;
	 month = m + 3 - 12 * (m / 10);
	 year = 100 * b + d - 4800 + m / 10;
	 
	 Day newDay = new Day(year, month, day); // returns a new day instance with the added days
	 
	 return newDay;
	 
  }
 
  
  // Compares if a current instance is before an argument instance 
  public boolean before(Day d) {
  
    if (JDinstance() < JDargument(d)) {
	   return true;
	 }
	 else {
	   return false;
	 }
	 
  }
 
  
  // Compares if a current instance instance is after an argument instance
  public boolean after(Day d) {
    
	 if (JDinstance() > JDargument(d)) {
	   return true;
	 }
	 else {
	   return false;
	 }
	 
  }
	
	 
  // Compares if the current instance is equal to the argument instance
  public boolean equals(Day d) {
    
	 if (JDinstance() == JDargument(d)) {
	   return true;
    }
	 else {
	   return false;
	 }
	 
  }
  
  
  /* Returns a positive number if the current instance is greater then, negative if 
   * the instance is less then, and zero if the instance is equal when compared to
	* another instance.
	*/
  public int compareTo(Day d) {
    
	 if (JDinstance() == JDargument(d)) {
	   return 0;
	 }
	 else if (JDinstance() > JDargument(d)) {
	   return 1;
	 }
	 else if (JDinstance() < JDargument(d)) {
	   return -1;
	 }
	 else {
	   return 0;
	 }
	 
  }
  
  
  // Print Identification Information
  static void classId() {
    
	 System.out.println("Jacob S. Howarth \n CS-101-07L, Spring 2007 \n Lab 7 -- Day Class \n");
  
  }
  
  
  // Prints the each instances month, day, and year
  public String toString() {
    
	 return this.month + "/" + this.day + "/" + this.year;
  
  }
  
  
  // Calculation algorithm for the current day constructor	 
  private void JDCalcToday() {
  
    long currentTime; // Total time in seconds from Jan. 1, 1970 to current days time
	 int totalDay; // coversion of total time to number of days (julian date)
    int JD; // calculation of Julian Date from JD of Jan. 1, 1970 and JD of current day 
    int a; // calculation variable used to convert JD to 0 arg constructors day, month and yr. 
    int b; // calculation variable used to convert JD to 0 arg constructors day, month and yr.
    int c; // calculation variable used to convert JD to 0 arg constructors day, month and yr.
    int d; // calculation variable used to convert JD to 0 arg constructors day, month and yr.
    int e; // calculation variable used to convert JD to 0 arg constructors day, month and yr.
    int m; // calculation variable used to convert JD to 0 arg constructors day, month and yr.
  
    currentTime = System.currentTimeMillis();
	 totalDay = (int)(currentTime / 1000 / 3600 / 24);
	 
	 JD = totalDay + EPOCH;
	 
	 a = JD + 32044;
	 b = (4 * a + 3) / 146097;
	 c = a - (146097 * b) / 4;
	 
	 d = (4 * c + 3) / 1461;
	 e = c - (1461 * d) / 4;
	 m = (5 * e + 2) / 153;
	 
	 day = e - (153 * m + 2) / 5 + 1;
	 month = m + 3 - 12 * (m / 10);
	 year = 100 * b + d - 4800 + m / 10;
  
  }
  
  
  // Julian date calculation for the instance day
  private int JDinstance() {
    int a,y,m; // calculation variables used to calculate the instances Julian Date
	 int JD; // Julian date of the instance
	 
	 a = (14 - this.month) / 12;
	 y = this.year + 4800 - a;
	 m = this.month + 12 * a - 3;
	 
	 JD = this.day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
	 
	 return JD;
	 
  }
  
  
  // Julian date calculation for the argument day
  private int JDargument(Day d) {
    int a,y,m; // calculation variables for conversion of Day d instance argument to Julian Date
	 int JDd; // Julian Date of Day d instance argument
	 
	 a = (14 - d.getMonth()) / 12;
	 y = d.getYear() + 4800 - a;
	 m = d.getMonth() + 12 * a - 3;
	 
	 JDd = d.getDay() + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
	 
	 return JDd;
	 
  }
} // close Day class 