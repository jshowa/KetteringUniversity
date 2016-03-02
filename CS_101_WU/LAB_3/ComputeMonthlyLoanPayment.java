/* Jacob S. Howarth
 * CS 101-05L, Fall 2007
 * Prof. Wu - 10/23/2007
 */
 
 /* -- Program Description --
  * This program will calculate the monthly and total payments of a loan amount during a loan period 
  * input by the user. The loan payments will be computed from 5% interest to 8% interest, incrementing every 
  * 1/8th percent. This program will also prevent the user from entering negative
  * loan amounts and loan periods.
  */

   import javax.swing.JOptionPane;
    public class ComputeMonthlyLoanPayment {  
       public static void main(String[] args) {
         													/* -- Variable Table -- */
         String outputDecision = "";				/* outputDecision = The string which stores the users input when asked to choose wether to print the loan/interest table in the console or a window. */
         String outputConsole = "console";		/* outputConsole = outputDecision is compared to outputConsole so the user is only allowed to type either "console" or "window" when prompt for output format of data. */
         String outputWindow = "window";			/* outputWindow = same as outputConsole description except this is what the user types if the data is to be output in a window. */
         String sJOptionPaneOutput = "";			/* sJOptionPaneOutput = string that contains the output loan/interest table displayed in a window. */
         String sLoanAmount;							/* sLoanAmount = user entered value of the loan amount of which the monthly payment will be calculated, entered as a string. */
         String sNumberOfYears;						/* sNumberOfYears = user entered value for the load period, entered as a string. */
         double loanAmount = 0;						/* loanAmount = a double containing the value of sLoanAmount after being parsed. */
         double numberOfYears = 0;					/* numberOfYears = a double containing the value of sNumberOfYears after being parsed. */
         double monthlyPayment = 0;					/* monthlyPayment = computed monthly payment of the loan amount over a number of years at a certain interest. */
         double monthlyInterestRate = 0;			/* monthlyInterestRate = taken from the annual interest rate of the loan. */
         double interestRatePercentage = 0;		/* interestRatePercentage = value to store the interest as a percent, used to store the converted annualInterestRate variable in the for loop when the monthly payment is calculated over varying interest. */
         double totalPayment = 0;					/* totalPayment = The monthly payment multiplied by the number of years multiplied by 12. */
         int placeCount = 1; 							/* placeCount = A counter to help format the monthly payment coloumn in a straight line when the output is printed in a window. Helps determine the appropriate spacing between the interest and the monthly payment coloumn when the table is created. */
         boolean yearsNegativeCheck = true;		/* yearsNegativeCheck = boolean flag variable to check if the loan period is negative when entered by the user. */
         boolean makeOutputDecision = true;		/* makeOutputDecision = boolean flag variable to check for incorrect input when user is asked to type "console" or "window" format to print the data. */
         
      
      // 1. Ask for user to input a non-negative loan amount and loan period (in years).
         do {
            sLoanAmount = JOptionPane.showInputDialog(null, "Please enter an integer, greater than zero for the loan amount: ",
               									 "Loan Input", JOptionPane.QUESTION_MESSAGE); // Ask user to enter input as a string
         											 
            loanAmount = Double.parseDouble(sLoanAmount);
         
         // 2. Perform error checking on loan amount and loan period for negative numbers, non-integers, and re-prompt for input if error is found.
            if (loanAmount <= 0 || loanAmount != (int)loanAmount) { // Check if the loan amount is negative or a non-integer number.
               JOptionPane.showMessageDialog(null, "Error: Please DO NOT input a loan amount that is a non-integer or is \n" + "less than or equal to zero, please re-enter loan amount.",
                  				"Windows System Error!", JOptionPane.ERROR_MESSAGE);
            } // close if
            else {
               while (yearsNegativeCheck) { // Control the loop with a boolean flag variable.
                  sNumberOfYears = JOptionPane.showInputDialog(null, "Please enter an integer, greater than zero for the loan period (in years): ",
                     							 "Loan Period Input", JOptionPane.QUESTION_MESSAGE); // Ask user for loan period input as string.
               
                  numberOfYears = Double.parseDouble(sNumberOfYears);
               
                  if (numberOfYears <= 0 || numberOfYears != (int)numberOfYears) { // Check if the loan period is negative or a non-integer number.
                     JOptionPane.showMessageDialog(null, "Error: Please DO NOT input a loan period that is a non-integer or is \n" + "less than or equal to zero, please re-enter loan period.",
                        		"Live Long and Prosper...", JOptionPane.ERROR_MESSAGE);
                  }
                  else 
                     yearsNegativeCheck = !yearsNegativeCheck; 
               } // close while
            } // close else
         } while (loanAmount <= 0 || loanAmount != (int)loanAmount);
      
         while (makeOutputDecision) { // Ask user if they want output printed in the console or in a window.
         
            outputDecision = JOptionPane.showInputDialog(null, "Would you like the resulting table output to the console or window? (type \"window\" for window output or \"console\" for console output): ",
               													"Beam me up Scotty...", JOptionPane.QUESTION_MESSAGE);
         
            if (outputDecision.equalsIgnoreCase(outputConsole) || outputDecision.equalsIgnoreCase(outputWindow)) // Re-loop until either the string "window" for results in window format or "console" for results in console format is typed. 
               makeOutputDecision = !makeOutputDecision;
            else
               JOptionPane.showMessageDialog(null, "Please type \"window\" or \"console\" as an output choice.", "I'm Captain Jean-Luc Picard of the U.S.S Enterprise...", JOptionPane.ERROR_MESSAGE);
         
         } // end while
      
      // 3. Print result in table format when output either to the console or window is chosen by the user.
         if (outputDecision.equalsIgnoreCase(outputConsole)) { // If console output is preferred, print the following table.
         
            System.out.println("LOAN AMOUNT: " + (int)loanAmount);
            System.out.println("LOAN PERIOD IN YEARS: " + (int)numberOfYears);
         
         // Make table heading
            for (int i = 1; i <= 20; i++) {
               System.out.print("-");
               if(i == 20)
                  System.out.print('\n');
            } // end for
         
            System.out.printf("%s\t\t", "ANNUAL INTEREST RATE");
            System.out.printf("%s\t\t", "MONTHLY PAYMENT");
            System.out.printf("%s\t\t\n", "TOTAL PAYMENT");
         
         
            for (double annualInterestRate = 5; 
             annualInterestRate <= 8; annualInterestRate = annualInterestRate + .125) { // for loop to increment annual interest from 5-8% incrementing 1/8% and calculating loan each time.
            
               interestRatePercentage = annualInterestRate / 100; // Make the annual interest into a percent.
               monthlyInterestRate = interestRatePercentage / 12; // Find the monthly interest.
            
               monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - 1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12));
               totalPayment = monthlyPayment * numberOfYears * 12; // Equation to calculate the monthly payments on the loan.
            
               System.out.printf("%.3f%%\t\t\t\t", annualInterestRate); // Format output to two places after the decimal.
               System.out.printf("%.2f\t\t\t", monthlyPayment);
               System.out.printf("%.2f", totalPayment);
               System.out.println();
            } // end for
         } // end if
         else {
         
            sJOptionPaneOutput = "LOAN AMOUNT:   " + (int)loanAmount + '\n';
            sJOptionPaneOutput += "LOAN PERIOD IN YEARS:   " + (int)numberOfYears + '\n';
         
         // Make table heading
            for (int j = 1; j <= 50; j++) {
               sJOptionPaneOutput += "-";
               if(j == 50)
                  sJOptionPaneOutput += '\n';
            } // end for
            
            sJOptionPaneOutput += "ANNUAL INTEREST RATE";
            sJOptionPaneOutput += "        " + "MONTHLY PAYMENT";
            sJOptionPaneOutput += "        " + "TOTAL PAYMENT" + '\n';
         
            for (double annualInterestRateJ = 5; 
             annualInterestRateJ <= 8; annualInterestRateJ = annualInterestRateJ + .125) { // for loop to increment annual interest from 5-8% incrementing 1/8% and calculating loan each time.
            
               monthlyPayment = 0;			// Variables initialized to make sure they are zero before calculations are made.
               monthlyInterestRate = 0;
               interestRatePercentage = 0;
               totalPayment = 0;
            
               interestRatePercentage = annualInterestRateJ / 100; // Make the annual interest into a percent.
               monthlyInterestRate = interestRatePercentage / 12; // Find the monthly interest.
            
               monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - 1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12));
               totalPayment = monthlyPayment * numberOfYears * 12; // Equation to calculate the monthly payments on the loan.
            	
               if (placeCount == 1) {
                  sJOptionPaneOutput += annualInterestRateJ + "%"; // Data output to window when the format counter is 1, meaning this much space should be between the monthly payment and the annual interest rate when concatenated into sJOptionPaneOutput.
                  sJOptionPaneOutput += "                                                         " + 
                     					(int)(monthlyPayment * 100) / 100.0;
                  sJOptionPaneOutput += "                           " + 
                     					(int)(totalPayment * 100) / 100.0 + '\n';
               } // end if
               else if (placeCount >= 2 && placeCount % 2 == 0) {
                  sJOptionPaneOutput += annualInterestRateJ + "%"; // If  the counter is greater than 2 and even, subtract two spaces between the monthly payment and annual interest rate when printed. 
                  sJOptionPaneOutput += "                                                     " + 
                     					(int)(monthlyPayment * 100) / 100.0;
                  sJOptionPaneOutput += "                           " + 
                     					(int)(totalPayment * 100) / 100.0 + '\n';
               } // end else if
               else if (placeCount == 3 || placeCount == 7 || placeCount == 11 || placeCount == 15 || placeCount == 19 || placeCount == 23) {
                  sJOptionPaneOutput += annualInterestRateJ + "%"; // If the counter is any of these numbers, subtract only one space between annual interest rate and monthly payment when printed.
                  sJOptionPaneOutput += "                                                        " + 
                     					(int)(monthlyPayment * 100) / 100.0;
                  sJOptionPaneOutput += "                           " + 
                     					(int)(totalPayment * 100) / 100.0 + '\n';
               }
               else {
                  sJOptionPaneOutput += annualInterestRateJ + "%"; // If the counter is odd, subtract only one space between annual interest rate and monthly payment when printed.
                  sJOptionPaneOutput += "                                                         " + 
                     					(int)(monthlyPayment * 100) / 100.0;
                  sJOptionPaneOutput += "                           " + 
                     					(int)(totalPayment * 100) / 100.0 + '\n';
               }
            	
               placeCount++; // Increment counter to keep up with loop.
            	
            } // end for
            
            JOptionPane.showMessageDialog(null, sJOptionPaneOutput, "KHAN!!!!", JOptionPane.INFORMATION_MESSAGE); // Print fromatted result table in window.
         	
         } // end else
      } // end main
   } // end class

