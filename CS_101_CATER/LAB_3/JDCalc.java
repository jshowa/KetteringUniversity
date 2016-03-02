/* Jacob Howarth
 * CS-101-07L, Spring 2007
 * Lab 1 -- Gregorian to Julian Date Converter
 */
 
/* This program will calculate the number of days between two Gregorian calendar dates by
 * converting them to Julian Dates (JD). The JD's will then be subtracted to find
 * the difference. The difference equals the total number of days between the Gregorian dates.
 * The day, month, and year will be prompted for each Gregorian date. 
 * The Gregorian dates will be given in standard input. All Gregorian dates will be A.D. 
 * and converted to Julian date at noon time. 
 */
 
 
import java.util.Scanner; // Scanner is in java.util library
 
class JDCalc {

    public static void main(String [] args) {
	    int JD1; // Gregorian to JD conversion for later date
		 int JD2; // Gregorian to JD conversion for earlier date
		 int day1; // first (later) Gregorian day
		 int month1; // first (later) Gregorian month
		 int year1; // first (later) Gregorian year
		 int day2; // second (earlier) Gregorian day
		 int month2; // second (earlier) Gregorian month
	    int year2; // second (earlier) Gregorian year
		
		// Print Identification Information.
		System.out.println("Jacob Howarth");
		System.out.println("CS-101-07, Spring 2007");
		System.out.println("Lab 2 -- Gregorian to JD converter");
		
		// Scanner creation 
		Scanner input = new Scanner(System.in);
		
		// Input day for first Gregorian date. 
		System.out.print("\nEnter later Gregorian day, for example 5: ");
		day1 = input.nextInt();
		
		// Input month for first Gregorian date.
		System.out.print("Enter later Gregorian month, for example 1 = January: ");
		month1 = input.nextInt();
		
		// Input year for first Gregorian date.
		System.out.print("Enter the later Gregorian year, for example yyyy: ");
		year1 = input.nextInt();
		
		int a = (14 - month1)/12; // Calculation for variable "a" used in equation "b".
		int b = year1 + 4800 - a; // Calculation for variable "b" used in JD1 conversion.
		int c = month1 + 12*a - 3; // Calculation for variable "c" used in JD1 conversion.
		
		// JD1 (First Julian Date) conversion.
		JD1 = day1 + (153 * c)/5 + 365 * b + b/4 - b/100 + b/400 - 32045;
		
		// Input day for second Gregorian date.
		System.out.print("\nEnter the earlier Gregorian day: ");
		day2 = input.nextInt();
		
		// Input month for second Gregorian date.
		System.out.print("Enter the earlier Gregorian month: ");
		month2 = input.nextInt();
		
		// Input year for second Gregorian date.
		System.out.print("Enter the earlier Gregorian year: ");
		year2 = input.nextInt();
		
		int d = (14 - month2)/12; // Calculation for variable "d" used in equation "e".
		int e = year2 + 4800 - d; // Calculation for variable "e" used in JD2 conversion.
		int f = month2 + 12*d - 3; // Calculation for variable "f" used in JD2 conversion.
		
		// JD2 (Second Julian Date) conversion.
		JD2 = day2 + (153 * f)/5 + 365 * e + e/4 - e/100 + e/400 - 32045;
		
		// Julian date difference.
		int diff = JD1 - JD2; // Total days between the two converted Gregorian dates.
		
		// Conversion result.
		System.out.print("A total of " + diff + " days have pasted between " + month2 + "/"
								 + day2 + "/" + year2 + " and " + month1 + "/" + day1 + "/" + year1 + ". ");
	  }
   }
		
		
							  
		
		  