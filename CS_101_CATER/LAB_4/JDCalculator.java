/* Jacob S. Howarth
 * CS 101-07L, Spring 2007
 * Lab 3 -- Julian/Gregorian JD Calculator
 */
 
/* This program will find the total days between two calendar dates by
 * converting the dates to Julian Date (JD). The dates will come from
 * Julian calendar or Gregorian calendar from the time the English converted
 * from Julian calendar to Gregorian in Sept. 2 1752. The dates, A.D. or B.C.,
 * and the time will be prompted for at the console. The program runs on a 12 hr. clock.
 */
 
import java.util.Scanner; // Scanner is imported from java.util library

class JDCalculator {

   public static void main (String [] args) {
	double JD1;        // Later calendar date.
	double JD2;        // Earlier calendar date.
	int year1;         // Later calendar date year.
	double day1;       // Later calendar date day.
	int month1;        // Later calendar date month.
	double hour1;      // Later calendar date hour.
	double min1;       // Later calendar date minute.
	int year2;         // Earlier calendar date year.
	double day2;       // Earlier calendar date day.
	int month2;        // Earlier calendar date month.
	double hour2;      // Earlier calendar date hour.
	double min2;       // Earlier calendar date minute.
	int cald = 1;      // calender 1 is gregorian, calender 2 is julian
	int a1;            // variable calculation for later date conversion algorithm.
	int b1;            // variable calculation for later date conversion algorithm.
	int c1;            // variable calculation for earlier date conversion algorithm.
	int x2;            // variable calculation for later date conversion algorithm.
	int y2;            // variable calculation for later date conversion algorithm.
	int z2;            // variable calculation for later date conversion algorithm.
	int year1A;        // new year value for later date printed in results after previous year value was changed.
	int year2A;        // new year variable for earlier date printed in results after previous year value was changed.
	String period1;    // AD or BC era string for later date.
	String period2;    // AD or BC string for earlier date.
	String period3;    // AM or PM string for later date.
	String period4;    // AM or PM string for earlier date. 
	String PM1;        // PM time string for later date.
	String AM1;        // AM time string for later date.
	String PM2;        // PM time string for earlier date.
	String AM2;        // AM time string for earlier date.
	String AD1;        // AD string variable for later date.
	String BC1;        // BC string variable for later date.
	String AD2;        // AD string variable for earlier date.
	String BC2;        // BC string variable for earlier date.
	String rerun = "y";  // String for recalculation.
	String yes = "y";    // Variable for recalculating a new date difference. 
	String no = "n";     // Variable for program termination. 
	
	while (yes.equalsIgnoreCase(rerun)) {
	
	// Print Indentification Information
	System.out.println(" Jacob Howarth ");
	System.out.println(" CS-101-07L, Spring 2007 ");
	System.out.println(" Lab 3 -- Julian/Gregorian JD Calculator ");
	
	// Scanner Creation
	Scanner input = new Scanner(System.in);
	
	//***FIRST DATE***
	
	// input later calendar date day
	System.out.print(" \nEnter the later day: ");
	day1 = input.nextInt();
	
	while (day1 <= 0) {
	System.out.print(" Days cannot be negative or 0, please enter a new day ");
	System.out.print(" \nEnter the later day: ");
	day1 = input.nextInt();
	}
	
	if (day1 > 31) {
	System.out.print(" Days cannot be greater than 31, enter a new day ");
	System.out.print(" \nEnter the later day: ");
	day1 = input.nextInt();
	}
	
	// Input later calander date month
	System.out.print(" Enter the later month: ");
	month1 = input.nextInt();

	while (month1 <= 0) {
	System.out.print(" Months cannot be negative or 0, please enter a new month ");
	System.out.print(" \nEnter the later month: ");
	month1 = input.nextInt();
	}
	
	if (month1 >= 13) {
	System.out.print(" Months cannot be greater than or equal to 13, enter a valid month ");
	System.out.print(" \nEnter the later month: ");
	month1 = input.nextInt();
	}
	
	// Accounting for Feburary days.
	while (month1 == 2 && day1 > 29) {
	System.out.print(" The day for Feburary cannot be greater then 29 ");
	System.out.print(" \nEnter a new day: ");
	day1 = input.nextInt();
	
	System.out.print(" \nEnter the later month: ");
	month1 = input.nextInt();
	}
	
	// Input later calander date year.
	System.out.print(" Enter the later year: ");
	year1 = input.nextInt();	
	
	while (year1 <= 0) {
	System.out.print(" Years cannot be negative or zero, enter a new year ");
	System.out.print(" \nEnter the later year: ");
	year1 = input.nextInt();
	}
	
	// Creating String for years in AD and BC for later calander date
	System.out.print(" Enter the period (AD1 or BC1): ");
	period1 = input.next();
	AD1 = "ad1";
	BC1 = "bc1";
	
	while (AD1.equalsIgnoreCase(period1)==false && BC1.equalsIgnoreCase(period1)==false) {
	System.out.print(" Invalid Input, enter BC1 or AD1 ");
	period1 = input.next();
	}
	
	// Parameters for specifying calender for later date because the date can be in Greg or Julian.
	
	//AD dates parameter.
	if (AD1.equalsIgnoreCase(period1)) {	
	if (year1 > 1752)
	cald = 1;
	else if (year1 == 1752) {
	if (month1 > 9)
	cald = 1;
	else if (month1 < 9)
	cald = 2;
	else if (month1 == 9 && day1==1)
	cald = 2;
	else
	cald = 1;
	}
	}
	else { 
	cald = 2;
	} 
	if (year1 < 1752) {
	cald = 2;
	}
	
	// BC dates
	if (BC1.equalsIgnoreCase(period1)) {
	cald = 2;
	year1 = (year1 - 1)*-1;
	}
	
	// Print calendar and year notification date.
	if (cald == 1) {
	System.out.println("Gregorian" + " " + year1);
	}
	else {
	System.out.println("Julian" + " " + year1);
	}
	
	//Creating hour input for later date.
	System.out.print( " Please enter the hour: ");
	hour1 = input.nextInt();
	
	while (hour1 > 12 || hour1 < 1) {
	System.out.print( " Please enter a valid hour from 1 to 12: " );
	hour1 = input.nextInt();
	}
	
	//Creating string for AM and PM for later date time.
	System.out.print(" Enter the time period, AM or PM: ");
	period3 = input.next();
	PM1 = "pm";
	AM1 = "am";
	while (AM1.equalsIgnoreCase(period3) == false && PM1.equalsIgnoreCase(period3) == false) {
	System.out.print( " Invalid input enter AM or PM: ");
	period3 = input.next();
	}
	
	// Partial day calculation based on hour input from Julian day starting at noon.
	if (AM1.equalsIgnoreCase(period3)) {
	day1 -= 0.5;
	}
	else if (hour1!=12){
	day1 = day1 + (hour1 / 24);
	}
	
	// Creating minutes input for later date.
	System.out.print( " Please enter the minutes: ");
	min1 = input.nextInt();
	
	while (min1 > 59 || min1 < 0) {
	System.out.print ( " Please enter a valid minute number between 0 and 59: ");
	min1 = input.nextInt();
	}
	
	// Later date minute calculation.
	day1 = day1 + ((min1/60)/24);
	  
	//***SECOND DATE***
	
	// Input earlier calendar date day
	System.out.print(" \nEnter the earlier day: ");
	day2 = input.nextInt();
	
	while (day2 <= 0) {
	System.out.print(" Days cannot be negative or 0, please enter a valid day ");
	System.out.print(" \nEnter the earlier day: ");
	day2 = input.nextInt();
	}
	
	if (day2 > 31) {
	System.out.print(" Days cannot be greater than 31, enter a valid day ");
	System.out.print(" \nEnter the earlier day: ");
	day2 = input.nextInt();
	}
	
	// Input earlier calendar date month
	System.out.print(" Enter the earlier month: ");
	month2 = input.nextInt();

	while (month2 <= 0) {
	System.out.print(" Months cannot be negative or 0, please enter a valid month ");
	System.out.print(" \nEnter the earlier month: ");
	month2 = input.nextInt();
	}
	
	if (month2 >= 13) {
	System.out.print(" Months cannot be greater than 13, enter a valid month ");
	System.out.print(" \nEnter the earlier month: ");
	month2 = input.nextInt();
	}
	
	// Accounting for Feburary days.
	while (month2 == 2 && day2 > 29) {
	System.out.print(" The day for Feburary cannot be greater then 29 ");
	System.out.print(" \nEnter a new day: ");
	day2 = input.nextInt();
	
	System.out.print(" \nEnter the earlier month: ");
	month2 = input.nextInt();
	}
	
	// Input earlier calendar date year.
	System.out.print(" Enter the earlier year: ");
	year2 = input.nextInt();	
	
	while (year2 <= 0) {
	System.out.print(" Years cannot be negative or zero, enter a new year ");
	System.out.print(" \nEnter the earlier year: ");
	year2 = input.nextInt();
	}
	
	// Creating String for Years in AD and BC for earlier date.
	System.out.print(" Enter the period (AD2 or BC2): ");
   period2 = input.next();
	AD2 = "ad2";
	BC2 = "bc2";
	while (AD2.equalsIgnoreCase(period2)==false && BC2.equalsIgnoreCase(period2)==false) {
	System.out.print(" Invalid Input, enter BC2 or AD2 ");
	period2 = input.next();
	}
	
	// Parameters for specifying calender for earlier date because date can be in Greg or Julian.
	
	// AD dates.
	if (AD2.equalsIgnoreCase(period2)) {	
	if (year2 > 1752)
	cald = 1;
	else if (year2 == 1752){
	if (month2 > 9)
	cald = 1;
	else if (month2 < 9)
	cald = 2;
	else if (month2 == 9 && day2==1)
	cald = 2;
	else
	cald = 1;
	} 
	else if (year2 < 1752)
	cald = 2;
	}
	else {
	cald = 2;
	}
	
	// BC dates.
	if (BC2.equalsIgnoreCase(period2)) {
	cald = 2;
	year2 = (year2 - 1)*-1;
	}
	
	// Print notficiation of calendar and year.
	if (cald == 1) {
	System.out.println("Gregorian" + " " + year2);
	}
	else {
	System.out.println("Julian" + " " + year2);
	}

	// Creating hour for earlier date input.
	System.out.print( " Please enter the hour: ");
	hour2 = input.nextInt();
	
	while (hour2 > 12 || hour2 < 1) {
	System.out.print( " Please enter a valid hour from 1 to 12: " );
	hour2 = input.nextInt();
	}
	
	// Creating string for AM and PM for earlier date.
	System.out.print(" Enter the time period, AM or PM: ");
	period4 = input.next();
	PM2 = "pm";
	AM2 = "am";
	while (AM2.equalsIgnoreCase(period4) == false && PM2.equalsIgnoreCase(period4) == false) {
	System.out.print( " Invalid input enter AM or PM: ");
	period4 = input.next();
	}
	
	// Partial day calculation based on hour input from Julian day starting at noon.
	if (AM2.equalsIgnoreCase(period3)) {
	day2 -= 0.5;
	}
	
	if (hour2!=12){
	day2 = day2 + (hour2 / 24);
	}
	
	// Creating minutes input for earlier date.
	System.out.print( " Please enter the minutes: ");
	min2 = input.nextInt();
	
	while (min2 > 59 || min2 < 0) {
	System.out.print ( " Please enter a valid minute number between 0 and 59: ");
	min2 = input.nextInt();
	}
	
	// Earlier date minute calculation.
	day2 = day2 + ((min2/60)/24);
	
	// Do first Julian Date (JD1) variable calculations.
   a1 = (14 - month1)/12;
	b1 = year1 + 4800 - a1;
	c1 = month1 + 12 * a1 - 3;
	
	// JD1 calculation based on calendar.
	if (cald == 1) { 
	JD1 = day1 + (135 * c1 + 2)/5 + 365 * b1 + (b1 / 4) - (b1 / 100) +
	             (b1 / 400) - 32045;
	}
	else {
	JD1 = day1 + (153 * c1 + 2)/5 + 365 * b1 + (b1 / 4) - 32083;
	}
	
	// Do second Julian Date (JD2) variable calculations.
	x2 = (14 - month2)/12;
	y2 = year2 + 4800 - x2;
	z2 = month2 + 12 * x2 - 3;
	
	// JD2 calculation based on calendar.
	if (cald == 1) { 
	JD2 = day2 + (135 * z2 + 2)/5 + 365 * y2 + (y2 / 4) - (y2 / 100) +
	             (y2 / 400) - 32045;
	}
	else {
	JD2 = day2 + (153 * z2 + 2)/5 + 365 * y2 + (y2 / 4) - 32083;
	}
	
	// Difference of the Julian Dates (JD1 - JD2) equals total between days.
	double diff = Math.abs(JD1 - JD2);
	
	// Cast day1 and day2 into the int type to show proper date in results. 
	int day1A = (int)day1;
	int day2A = (int)day2;
	
	// Cancelling the negative in the year when printing results.
	if (AD1.equalsIgnoreCase(period1) || AD2.equalsIgnoreCase(period2)) {
	year1A = year1;
	year2A = year2;
	}
	else {
	year1A = -(year1);
	year2A = -(year2);
	}
	
	// Print results. 
	System.out.print( "\nThere is a total of " + diff + " days between dates " + month1 + "/" +
                    day1A + "/" + year1A + " " + period1 + " at the time " + hour1 + " " + 
						  period3 + " and\n " + min1 + " minutes and " + month2 + "/" + day2A + "/" +
						  year2A + " " + period2 + " at the time " + hour2 + " " + period4 + " and " +
						  min2 + " minutes. ");
	
	// Request new calculation.
	System.out.print( "\nWould you like to make another calculation, yes or no? (y/n): ");
	rerun = input.next();
  }
 }
}


	

	
	
	
		
	
	
	
	
	
	 
	
	

	
	
	 
	
					  
	                 