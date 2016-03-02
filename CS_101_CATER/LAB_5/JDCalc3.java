/* Jacob S. Howarth
 * CS-101-07L, Spring 2007
 * Lab 4 -- Julian Date Calculator (3.0v)
 */
 
/* This program will calculate the difference between two dates from either the
 * Gregorian or Julian calander to find the total number of days. The input will be
 * read in on one line in the form of era, year, month name, month day, and military time.
 * The time is on a 24 hour clock.
 */
 
   import java.util.Scanner;

    public class JDCalc3 { // class open
   /* Main Method */
       public static void main (String[] args) { // main open
         double diff;
         double JD1;
         double JD2;
         double dayNum1;
         double dayNum2;
         int yearNum1;
         int yearNum2;
         double hourNum1;
         double hourNum2;
         double minNum1;
         double minNum2;
         String day1;
         int month1;
         String year1;
         String monthName;
         String min1;
         String hour1;
         String day2;
         String monthName2;
         int month2;
         String year2;
         String min2;
         String hour2;
         String era;
         String era2;
         String AD = "AD";
         String BC = "BC";
         int cald = 1;
         String rerun;
         String yes = "y";
         String no = "n";
      
      /* Personal Identification Info */
         System.out.println(" Jacob Howarth ");
         System.out.println(" CS-101-07L, Spring 2007 ");
         System.out.println(" Lab 4 -- Julian Date Calander ");
      
      
         while (true) { // While True1 Program Loop Open
         
          /* Initial Question for Input of First Date */
            System.out.println("\nEnter the first date in the following order seperated by spaces, ");
            System.out.println(" ERA, YEAR, MONTH NAME, MONTH DAY, MILITARY TIME (HR and MIN): ");
         
         /* Scanner Creation */
            Scanner line = new Scanner(System.in); // Reads input from console
            String aLine = line.nextLine(); // Reads input as a line
            Scanner input = new Scanner(aLine); // Turns input line into a scanner
         
         /* Input on one line for First Date */
            era = input.next();
            year1 = input.next();
            monthName = input.next();
            day1 = input.next();
            input.useDelimiter(":"); // Scanner delimiter change for time
            hour1 = input.next();
            min1 = input.next();
         
         /* Intitial Question for Input of Second Date */
            System.out.println("Enter the second date in the following order seperated by spaces, ");
            System.out.println(" ERA, YEAR, MONTH NAME, MONTH DAY, MILITARY TIME (HR and MIN): ");
         
         /* Scanner Reinitialization */
            line = new Scanner(System.in);
            aLine = line.nextLine();
            input = new Scanner(aLine);
         
         /* Input on one line for Second Date */
            era2 = input.next();
            year2 = input.next();
            monthName2 = input.next();
            day2 = input.next();
            input.useDelimiter(":"); // Scanner delimiter change for time
            hour2 = input.next();
            min2 = input.next();
         
         
            while (true) { // While True2 Validation Loop Open
            
            
               month1 = Math.abs(getMonthNum(monthName));
               month2 = Math.abs(getMonthNum(monthName2));
               dayNum1 = Math.abs(getParseDouble(day1));
               dayNum2 = Math.abs(getParseDouble(day2));
               yearNum1 = Math.abs(getParseInt(year1));
               yearNum2 = Math.abs(getParseInt(year2));
               hourNum1 = Math.abs(getParseHour(hour1));
               hourNum2 = Math.abs(getParseHour(hour2));
               minNum1 = Math.abs(getParseDouble(min1));
               minNum2 = Math.abs(getParseDouble(min2));
            
            
       if (yearValidate(year1) == true && yearValidate(year2) == true &&  // Validates for true years for both dates 
       eraValidate(era2) == true && eraValidate(era) == true &&  // Validates for true eras for both dates
       dayValidate(day1) == true && dayValidate(day2) == true && // Validates for true days for both dates
       monthValidate(monthName) == true && monthValidate(monthName2) == true // Validates for true month names for both dates
       && getMonthNum(monthName) != 0 && getMonthNum(monthName2) != 0 &&  // Validates for month number != 0 for both dates
       minuteValidate(min1) == true && minuteValidate(min2) == true &&  // Validates for true minutes for both dates
       hourValidate(hour1) == true && hourValidate (hour2) == true && // Validates for true hours for both dates
       yearNum1 != 0 && yearNum2 != 0 &&  // Validates for non-zero years for both dates
       dayNum2 > 0 && dayNum2 <= 31 && dayNum1 > 0 && dayNum1 <= 31 &&  // Validates for days less than or equal to zero or greater then 31
       hourNum1 <= 24 && hourNum2 <= 24 &&  // Validates for hours less than or equal to 24 for both dates
       minNum1 <= 59 && minNum2 <= 59) {  // Validates for minutes less than or equal to 59 for both dates
       break; // When true, break Validation Loop
       }
            	
            	            
               if (yearValidate(year1) == false) { // Print error if first date year is false
                  System.out.println("Year for first date is wrong! Reinput year!");
               } 
               else if (yearValidate(year2) == false) { // Print error if second date year is false 
                  System.out.println("Year for second date is wrong! Reinput year!");
               }
            
               if (eraValidate(era) == false)  { // Print error for era validation if "ad" or "bc" is false for first date 
                  System.out.println("Era for first date is wrong! Reinput era!");
               }
               else if (eraValidate(era2) == false) { // Print error for era validation if "ad" or "bc" is false for second date.
                  System.out.println("Era for second date is wrong! Reinput era!");  
               }
            
               if (dayValidate(day1) == false) { // Print error if day validation entered as characters for first date
                  System.out.println("Day for first date is wrong! Reinput day!");
               }
               else if (dayValidate(day2) == false) { // Print error if day validation entered as characters for second date
                  System.out.println("Day for second date is wrong! Reinput day!");
               }
            
               if (monthValidate(monthName) == false) { // Print error if month name for first date is entered as a number
                  System.out.println("Month name for first date is wrong! Reinput month!"); 
               } 
               else if (monthValidate(monthName2) == false) { // Print error if month name for second date is entered as a number 
                  System.out.println("Month name for second date is wrong! Reinput month!");
               }
            
               if (hourValidate(hour1) == false) { // Print error if hour validation for first date entered as characters 
                  System.out.println("Hour is wrong for first date! Reinput hour!");
               }
               else if (hourValidate(hour2) == false) { // Print error if hour validation for second date is entered as characters
                  System.out.println("Hour is wrong for second date! Reinput hour!");
               }
            
               if (minuteValidate(min1) == false) { // Print error if minute validation for first date is entered as characters
                  System.out.println("Minute is wrong for first date! Reinput minute");
               }
               else if (minuteValidate(min2) == false) { // Print error if minute validation for second date is entered as characters
                  System.out.println("Minute is wrong for second date! Reinput minute");
               }
            
               if (getMonthNum(monthName) == 0) { // Print error if month int return for first date is 0 from "getMonthNum" method 
                  System.out.println("Please spell the month correctly for first date!");
               }
               else if (getMonthNum(monthName2) == 0) { // Print error if month in return for second date is 0 from "getMonthNum" method
                  System.out.println("Please spell the month correctly for second date!");
               }
            	
               if (yearNum1 == 0 || yearNum2 == 0) { // Print error if years are equal to zero
                  System.out.println("Years cannot be negative or zero please reinput years!");
               }
            	
               if (dayNum1 == 0 || dayNum1 > 31) { // Print error if days are less than zero or greater than 31 for first date
                  System.out.println("Day for first date cannot be equal to 0 or greater than 31, please re-enter first date.");
               }
               else if (dayNum2 == 0 || dayNum2 > 31) { // Print error if days are less than zero or greater than 31 for second date
                  System.out.println("Day for second date cannot be equal to 0 or greater than 31, please re-enter second date.");
               }
            	
               if (hourNum1 > 24) { // Print error if hour for first date is greater than 24
                  System.out.println("Hour cannot be greater than 24, please re-enter first date.");
               }
               else if (hourNum2 > 24) { // Print error if hour for second date is greater than 24
                  System.out.println("Hour cannot be greater than 24, please re-enter second date.");
               }
            	
               if (minNum1 > 59) { // Print error if minutes for first date is greater than 59
                  System.out.println("Minutes cannot be greater than 59, please re-enter first date.");
               }
               else if (minNum2 > 59) { // Print error if minutes for second date is greater than 59
                  System.out.println("Minutes cannot be greater than 59, please re-enter second date.");
               }
            
               if (yearValidate(year1)== false || yearValidate(year2) == false || // Validates for false years for both dates
               eraValidate(era) == false || eraValidate(era2) == false ||  // Validates for false eras for both dates
               monthValidate(monthName) == false || monthValidate(monthName2) == false ||  // Validates for false month names for both dates
               dayValidate(day1) == false || dayValidate(day2) == false ||  // Validates for false days in both dates
               minuteValidate(min1) == false || minuteValidate(min2) == false || // Validates for false minutes in both dates
               hourValidate(hour1) == false || hourValidate(hour2) == false ||  // Validates for false hours in both dates
               getMonthNum(monthName) == 0 || getMonthNum(monthName2) == 0 ||  // Validates for month number = to 0 for both dates
               yearNum1 == 0 || yearNum2 == 0 ||  // Validate for year number equal to zero for both dates
               dayNum1 == 0 || dayNum1 > 31 || // Validate for day number equal to zero or greater than 31 for first date 
               dayNum2 == 0 || dayNum2 > 31 || // Validate for day number equal to zero or grather than 31 for second date
               hourNum1 > 24 || hourNum2 > 24 || // Validate for hours greater than 24 for both dates
               minNum1 > 59 || minNum2 > 59) {  // Validate for minutes greater than 59 for both dates           
               /* Restate Intialize Question for Invalid Inputs for First Date */
                  System.out.println(" Re-enter for first date ERA, YEAR, MONTH NAME, MONTH DAY, MILITARY TIME (HR and MIN): ");
               
               /* Scanner Reinitialize */
                  line = new Scanner(System.in);
                  aLine = line.nextLine();
                  input = new Scanner(aLine);
               
               /* Input on one line for First Date */
                  era = input.next();
                  year1 = input.next();
                  monthName = input.next();
                  day1 = input.next();
                  input.useDelimiter(":");
                  hour1 = input.next();
                  min1 = input.next();
               
               /* Restate Intialize Question for Invalid Inputs for Second Date */
                  System.out.println(" Re-enter for second date ERA, YEAR, MONTH NAME, MONTH DAY, MILITARY TIME (HR and MIN): ");
               
               /* Scannner Reinitialization */
                  line = new Scanner(System.in);
                  aLine = line.nextLine();
                  input = new Scanner(aLine);
               
               /* Input on one line for Second Date*/
                  era2 = input.next();
                  year2 = input.next();
                  monthName2 = input.next();
                  day2 = input.next();
                  input.useDelimiter(":"); // Scanner delimiter change for time of second date
                  hour2 = input.next();
                  min2 = input.next();
                  continue; 
               }
            	            
            } // While True2 Validation Loop Closed
         
         // Terminate program for feburary validation
            if (month1 == 2 && dayNum1 > 28 && yearNum1 % 4 != 0 && yearNum1 % 400 != 0) {
               System.out.println("Invalid input for feburary due to a non leap year, program will terminate!");
               System.exit(0);
            }
            else if (month1 == 2 && dayNum1 > 29 && yearNum1 % 4 == 0 && yearNum1 % 400 == 0) {
               System.out.println("Invalid input for feburary during a leap year, program will terminate!");
               System.exit(0);
            }
            else if (month2 == 2 && dayNum2 > 28 && yearNum2 % 4 != 0 && yearNum2 % 400 != 0) {
               System.out.println("Invalid input for feburary due to a non leap year, program will terminate!");
               System.exit(0);
            }
            else if (month2 == 2 && dayNum2 > 29 && yearNum2 % 4 == 0 && yearNum2 % 400 == 0) {
               System.out.println("Invalid input for feburary during a leap year, program will terminate!");
               System.exit(0);
            }
         
            System.out.println("\n First date is: " + month1 + "/" + (int)dayNum1 + "/" + yearNum1 + " " 
                     + (int)hourNum1 + ":" + (int)minNum1 + "\n Second date is: " + month2 + "/" + (int)dayNum2 + "/" + yearNum2 + " " +
                     (int)hourNum2 + ":" + (int)minNum2);
         
             
         // AD calander change for first date
            if (AD.equalsIgnoreCase(getEra(era))) {	
               if (yearNum1 > 1752)
                  cald = 1;
               else if (yearNum1 == 1752){
                  if (month1 > 9)
                     cald = 1;
                  else if (month1 < 9)
                     cald = 2;
                  else if (month1 == 9 && dayNum1 == 1)
                     cald = 2;
                  else
                     cald = 1;
               } 
               else if (yearNum1 < 1752)
                  cald = 2;
            }
            else {
               cald = 2;
            }
         
         // BC calander for first date
            if (BC.equalsIgnoreCase(getEra(era))) {
               cald = 2;
               yearNum1 = (yearNum1 - 1)*-1;
            }
         
         // Print notficiation of calendar and year.
            if (cald == 1) {
               System.out.println(" Gregorian calendar" + " " + yearNum1 + " for first date.");
            }
            else {
               System.out.println(" Julian calendar" + " " + yearNum1 + " for first date.");
            }
         	
         	
         // AD calander change for first date
            if (AD.equalsIgnoreCase(getEra(era2))) {	
               if (yearNum2 > 1752)
                  cald = 1;
               else if (yearNum2 == 1752){
                  if (month2 > 9)
                     cald = 1;
                  else if (month2 < 9)
                     cald = 2;
                  else if (month2 == 9 && dayNum2 == 1)
                     cald = 2;
                  else
                     cald = 1;
               } 
               else if (yearNum2 < 1752)
                  cald = 2;
            }
            else {
               cald = 2;
            }
         
         // BC calander for first date
            if (BC.equalsIgnoreCase(getEra(era2))) {
               cald = 2;
               yearNum2 = (yearNum2 - 1)*-1;
            }
         
         // Print notficiation of calendar and year.
            if (cald == 1) {
               System.out.println(" Gregorian calendar" + " " + yearNum2 + " for second date.");
            }
            else {
               System.out.println(" Julian calendar" + " " + yearNum2 + " for second date.");
            }
         	
            double day1a = 0;
            double day2a = 0;
         	
         // Minute and hour additions to first date day
            if (hourNum1 > 12) {
               day1a = (dayNum1 + 1) - 0.5 + ((minNum1/60)/24) + (hourNum1/24);
            }
            else if (hourNum1 == 12) {
               day1a = dayNum1 + 1 + ((minNum1/60)/24);
            }
            else if (hourNum1 < 12) {
               day1a = dayNum1 + 0.5 + ((minNum1/60)/24) + (hourNum1/24);
            }
         	
         // Do first Julian Date (JD1) variable calculations.
            int a = (14 - month1)/12;
            int b = yearNum1 + 4800 - a;
            int c = month1 + 12 * a - 3;
         
         // JD1 calculation based on calendar.
            if (cald == 1) { 
               JD1 = day1a + (135 * c + 2)/5 + 365 * b + (b / 4) - (b / 100) +
                  (b / 400) - 32045;
            }
            else {
               JD1 = day1a + (153 * c + 2)/5 + 365 * b + (b / 4) - 32083;
            }
         
         // Validate JD1 calculation based on calendar
         
            System.out.println("\nJulian Date conversion for first date: " + JD1);
            
         // Minute and hour additions to second date day
            if (hourNum2 > 12) {
               day2a = (dayNum2 + 1) - 0.5 + ((minNum2/60)/24) + (hourNum2/24);
            }
            else if (hourNum2 == 12) {
               day2a = dayNum2 + 1 + ((minNum2/60)/24);
            }
            else if (hourNum2 < 12) {
               day2a = dayNum2 + 0.5 + ((minNum2/60)/24) + (hourNum2/24);
            }
         	
         // Do Second Julian Date (JD2) variable calculations.
            int d = (14 - month2)/12;
            int e = yearNum2 + 4800 - d;
            int f = month2 + 12 * d - 3;
         
         // JD2 calculation based on calendar.
            if (cald == 1) { 
               JD2 = day2a + (135 * f + 2)/5 + 365 * e + (e / 4) - (e / 100) +
                  (e / 400) - 32045;
            }
            else {
               JD2 = day2a + (153 * f + 2)/5 + 365 * e + (e / 4) - 32083;
            }
         	
         // Validate JD2 calculation based on calendar
         
            System.out.println(" Julian Date conversion for second date: " + JD2);
         
         // Find the total days between both dates by taking the difference
            diff = JD2 - JD1;
         	
         // Print Results
            System.out.println("\nThere is a total of " + diff + " day(s) between dates " + month1 + "/" +
                                 (int)dayNum1 + "/" + yearNum1 + " " + era + " " + (int)hourNum1 + ":" + (int)minNum1 +
               						" for first date");
            System.out.println("and " + month2 + "/" + (int)dayNum2 + "/" + yearNum2 + " " + era2 + " " + (int)hourNum2 +
                                ":" + (int)minNum2 + " for second date.");
         
         
         /* Reintialize Scanner */
            input = new Scanner(System.in);
         
         // Request another calculation
            System.out.println("Would you like to make another calculation? (yes or no): ");     
            rerun = input.next();
         	
            if (yes.equalsIgnoreCase(rerun)) {
            }
            else if (no.equalsIgnoreCase(rerun)) {
               System.exit(0);
            }
         	
         } // While True1 Program Loop Close
      
      
      
      }// main close
   	
       public static String getEra(String adbc) {
         String AD = "ad";
         String BC = "bc";
      	
         if (AD.equalsIgnoreCase(adbc)) {
            return AD;
         }
         else {
            return BC;
         }
      } // close getEra
   	
   
   /* minuteValidate Method for checking if input for "min1" is a char or digit */
       public static boolean minuteValidate(String min1) {
         boolean v;
         v = true;
      
         for (int i = 0; i < min1.length(); i++) {
            char a = min1.charAt(i);
         
            if (Character.isDigit(a))
               v = true;
            else if (Character.isLetter(a))
               return false;
         }
         return v;
      } // close minuteValidate
   	
   /* hourValidate Method for checking if input for "hour1" is a char or digit */
       public static boolean hourValidate(String hour1) {
         boolean v;
         v = true;
      
         for (int i = 0; i < hour1.length(); i++) {
            char a = hour1.charAt(i);
         
            if (Character.isDigit(a))
               v = true;
            else if (Character.isLetter(a))
               return false;
         }
         return v;
      } // close hourValidate
   
   
   /* eraValidate Method for checking if input for "era" is a char or digit other then "ad" or "bc" */
       public static boolean eraValidate(String era) {
         String AD = "AD";
         String BC = "BC";
      
         if (AD.equalsIgnoreCase(era)==false && BC.equalsIgnoreCase(era)==false) {
            return false;
         }
         else {
            return true;
         }
      } // close eraValidate
    
   /* yearValidate Method for checking if input for "year1" is a char or digit */
       public static boolean yearValidate(String year1) {
         boolean v;
         v = true;
      
         for (int i = 0; i < year1.length(); i++) {
            char a = year1.charAt(i);
         
            if (Character.isDigit(a))
               v = true;
            else if (Character.isLetter(a))
               return false;
         }
         return v;
      } // close yearValidate
   
   /* monthValidate Method for checking if input for "month" is a char or digit */
       public static boolean monthValidate(String monthName) {
         boolean v;
         v = true;
      
         for (int i = 0; i < monthName.length(); i++) {
            char a = monthName.charAt(i);
         
            if (Character.isLetter(a))
               v = true;
            else if (Character.isDigit(a))
               return false;
         }
         return v;
      } // close monthNameValidate
   	
   /* dayValidate Method for checking if input for "day1" is a char or digit */
       public static boolean dayValidate(String day1) {
         boolean v;
         v = true;
      
         for (int i = 0; i < day1.length(); i++) {
            char a = day1.charAt(i);
         
            if (Character.isDigit(a))
               v = true;
            else if (Character.isLetter(a))
               return false;
         }
         return v;
      } // close dayValidate
   
   /* getMonthNum method for returning the month string input as the appropriate month integer */
       public static int getMonthNum(String monthName) {
         String month1 = "january";
         String month2 = "feburary";
         String month3 = "march";
         String month4 = "april";
         String month5 = "may";
         String month6 = "june";
         String month7 = "july";
         String month8 = "august";
         String month9 = "september";
         String month10 = "october";
         String month11 = "november";
         String month12 = "december";
         int result;
         result = 0;
      
         if (month1.equalsIgnoreCase(monthName)) {
            result = 1;
            return result;
         }
         else if (month2.equalsIgnoreCase(monthName)) {
            result = 2;
            return result;
         }
         else if (month3.equalsIgnoreCase(monthName)) {
            result = 3;
            return result;
         }
         else if (month4.equalsIgnoreCase(monthName)) {
            result = 4;
            return result;
         }
         else if (month5.equalsIgnoreCase(monthName)) {
            result = 5;
            return result;
         }
         else if (month6.equalsIgnoreCase(monthName)) {
            result = 6;
            return result;
         }
         else if (month7.equalsIgnoreCase(monthName)) {
            result = 7;
            return result;
         }
         else if (month8.equalsIgnoreCase(monthName)) {
            result = 8;
            return result;
         }
         else if (month9.equalsIgnoreCase(monthName)) {
            result = 9;
            return result;
         }
         else if (month10.equalsIgnoreCase(monthName)) {
            result = 10;
            return result;
         }
         else if (month11.equalsIgnoreCase(monthName)) {
            result = 11;
            return result;
         }
         else if (month12.equalsIgnoreCase(monthName)) {
            result = 12;
            return result;
         }
         else { 
            return result;
         }
      } // close getMonthNum
      
   	/* Parse the years, minutes, and hours into ints for both dates */
       public static int getParseInt(String parsedNum) {
         int parsedNumber;
      	
         parsedNumber = Integer.parseInt(parsedNum);
      	
         return parsedNumber;
      } // close getParseInt
     
      /* Parse the days for both dates as doubles */
       public static double getParseDouble(String parsedDouble) {
         double parsedNumber;
      	
         parsedNumber = Double.parseDouble(parsedDouble);
      	
         return parsedNumber;
      } // close getParseDouble
   	
     /* Return hour string from main as an int */
       public static double getParseHour(String hours) {
         int hourNumber = 30;
         String hour1 = " 0";
         String hour2 = " 1";
         String hour3 = " 2";
         String hour4 = " 3";
         String hour5 = " 4";
         String hour6 = " 5";
         String hour7 = " 6";
         String hour8 = " 7";
         String hour9 = " 8";
         String hour10 = " 9";
         String hour11 = " 10";
         String hour12 = " 11";
         String hour13 = " 12";
         String hour14 = " 13";
         String hour15 = " 14";
         String hour16 = " 15";
         String hour17 = " 16";
         String hour18 = " 17";
         String hour19 = " 18";
         String hour20 = " 19";
         String hour21 = " 20";
         String hour22 = " 21";
         String hour23 = " 22";
         String hour24 = " 23";
         String hour25 = " 24";
      
         if (hour1.equals(hours)) {
            hourNumber = 0;
            return hourNumber;
         }
         
         else if (hour2.equals(hours)) {
            hourNumber = 1;
            return hourNumber;
         }
         
         else if (hour3.equals(hours)) {
            hourNumber = 2;
            return hourNumber;
         }
         
         else if (hour4.equals(hours)) {
            hourNumber = 3;
            return hourNumber;
         }
         
         else if (hour5.equals(hours)) {
            hourNumber = 4;
            return hourNumber;
         }
         
         else if (hour6.equals(hours)) {
            hourNumber = 5;
            return hourNumber;
         }
         
         else if (hour7.equals(hours)) {
            hourNumber = 6;
            return hourNumber;
         }
         
         else if (hour8.equals(hours)) {
            hourNumber = 7;
            return hourNumber;
         }
         
         else if (hour9.equals(hours)) {
            hourNumber = 8;
            return hourNumber;
         }
         
         else if (hour10.equals(hours)) {
            hourNumber = 9;
            return hourNumber;
         }
         
         else if (hour11.equals(hours)) {
            hourNumber = 10;
            return hourNumber;
         }
         
         else if (hour12.equals(hours)) {
            hourNumber = 11;
            return hourNumber;
         }
         
         else if (hour13.equals(hours)) {
            hourNumber = 12;
            return hourNumber;
         }
         
         else if (hour14.equals(hours)) {
            hourNumber = 13;
            return hourNumber;
         }
         
         else if (hour15.equals(hours)) {
            hourNumber = 14;
            return hourNumber;
         }
         
         else if (hour16.equals(hours)) {
            hourNumber = 15;
            return hourNumber;
         }
         
         else if (hour17.equals(hours)) {
            hourNumber = 16;
            return hourNumber;
         }
         
         else if (hour18.equals(hours)) {
            hourNumber = 17;
            return hourNumber;
         }
         else if (hour19.equals(hours)) {
            hourNumber = 18;
            return hourNumber;
         }
         
         else if (hour20.equals(hours)) {
            hourNumber = 19;
            return hourNumber;
         }
         
         else if (hour21.equals(hours)) {
            hourNumber = 20;
            return hourNumber;
         }
         
         else if (hour22.equals(hours)) {
            hourNumber = 21;
            return hourNumber;
         }
         
         else if (hour23.equals(hours)) {
            hourNumber = 22;
            return hourNumber;
         }
         else if (hour24.equals(hours)) {
            hourNumber = 23;
            return hourNumber;
         }
         else if (hour25.equals(hours)) {
            hourNumber = 24;
            return hourNumber;
         }
         
         else { 
            return hourNumber;
         }
      } // close parseHour
   
   } // close class
	
	
	
	   

  
  
  
	 
