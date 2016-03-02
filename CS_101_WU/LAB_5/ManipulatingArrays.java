   import javax.swing.JOptionPane;
   import java.util.Arrays;

    public class ManipulatingArrays {
       public static void main(String[] args) {
      
      /* -- Variable Data Table --
       * ARRAY_SIZE = A constant value that establishes an array size of 10 for the random valued array pasted to method 1 and 2.
       * randomArray = An array created in main and filled in fillArray1D method with random numbers in order to test method 1, 2 and 3.  
       * sortedArray = Since arrays that are pasted to methods and changed within the methods get changed permanently due to heap storage, a new array was needed to store the sorted random array and to print the before and after images of the array for method 3.
       * randomArray2D = A 2D array needed for method 4 used to store the randomly generated row, column and valued 2D array from the fillArray2D method and pasted to the printArrayResult method.
       * result = holds the result of the index for method 1 and 2 and the sum from method 4
       * counter = used to print the appropriate result window and error messages used in the printArrayResult method since each method returns different things.
       */
       
         final int ARRAY_SIZE = 10; 
         int[] randomArray = new int[ARRAY_SIZE];
         int[] sortedArray = new int[ARRAY_SIZE];
         int[][] randomArray2D = new int[0][0];
         int result = 0;
         int counter = 1;
         
      	// Method 1 -- Find the index of the largest number in the array
         randomArray = fillArray1D(randomArray); // Step 1 -- Call the method fillArray1D and fill the randomArray [size 10] with 10 random values.
         result = indexOfLargestNumber(randomArray); // Step 2 -- Pass the randomArray to first method that finds the index of the first occurence of the largest array value and return the index.
         printArrayResults(randomArray, sortedArray, result, counter, randomArray2D); // Step 3 -- Print the array and the appropriate message for the index of the largest number in the printArrayResult method.
         counter++; // Step 4 -- increment the counter so when the next method is performed the appropriate error or result messages can be displayed for the paticular method.
      	
      	// Method 2 -- Find the index of the second largest number
         randomArray = fillArray1D(randomArray); // Step 1 -- Create another randomArray by calling fillArray1D again with randomArray and storing different random values inside.
         result = indexOfSecondLargestNumber(randomArray); // Step 2 -- Call the 2nd method and return the index of the second largest number.
         printArrayResults(randomArray, sortedArray, result, counter, randomArray2D);  // Step 3 -- Print the array and the appropriate message for the index of the second largest number in the printArrayResult method.
         counter++;  // Step 4 -- increment the counter so when the next method is performed the appropriate error or result messages can be displayed for the paticular method.
      	
      	// Method 3 -- Sort an Array with Random Values
         randomArray = fillArray1D(randomArray); // Step 1 -- Generate a new randomArray
         printArrayResults(randomArray, sortedArray, result, counter, randomArray2D); // Step 2 -- Pass it to the printArrayResult method so the array can be printed before it is sorted. The sortNumbers method [method 3] is called in the printArrayResult after the randomArray before sort is appended to the output string.
         counter++; // Step 4 -- increment the counter so when the next method is performed the appropriate error or result messages can be displayed for the paticular method.
      	
      	// Method 4 -- Sum the numbers in a 2D array with random values, rows, and columns.
         randomArray2D = fillArray2D(); // Step 1 -- Generate a 2D array with random values, rows, and columns
         result = sumGrid(randomArray2D); // Step 2 -- Call method 4 and return grid sum
         printArrayResults(randomArray, sortedArray, result, counter, randomArray2D); // Step 3 -- Print result of summation and array summed in 2D.
      
      // Call methods
      } // end main
   	 
       public static int[] fillArray1D(int[] randomArray) {
      
         for (int i = 0; i < randomArray.length - 1; i++) {
            randomArray[i] = (int)(Math.random() * 10); // fill the 1D random array for both method 1 and 2 with single digit values.
         }
      
         return randomArray;
      
      } // end fillArray
      
       public static int[][] fillArray2D() { // Method used for 2D array only.
      
         int row = (int)(Math.random() * 10);  // generate a random number of rows and columns for the 2D array
         int column = (int)(Math.random() * 10);
      
         while (row > 5 || row == 0 || column > 10 || column == 0) { // if the row is greater than 5 or zero or the column is greater than 10 or zero, recalculate the row and column for the 2D array until these conditions are false.
            row = (int)(Math.random() * 10);
            column = (int)(Math.random() * 10);
         }
      
         int[][] randomArray2D = new int[row][column]; // make a 2D array with the random number of rows and columns.
      
         for (int i = 0; i < randomArray2D.length; i++) {
            for (int j = 0; j < randomArray2D[i].length; j++) {
               randomArray2D[i][j] = (int)(Math.random() * 10); // fill each row and column with random values.
            } // end inner for
         } // end outer for
      
         return  randomArray2D;
      
      } // end fillArray2D
   
       public static int indexOfLargestNumber(int[] inputNumbers) { //***METHOD 1***
      
         int max = 0;	// max = current largest number in the array
         int index = 0; // index = stores the index (location) of the largest number in the array
      
         if (inputNumbers == null) { // if the 1D random value array (inputNumbers) is empty return an error.
            return -1;
         }
      
         for (int i = 0; i <= inputNumbers.length - 1; i++) {
            if (inputNumbers[i] > max) { // go through the array assigning each number greater than the number before to the max and returning the index of the max.
               max = inputNumbers[i];
               index = i;
            }
         } // end for
      
         return index;
      
      } // end indexOfLargestNumber
   
       public static int indexOfSecondLargestNumber(int[] inputNumbers) { //***METHOD 2***
       
         int max = 0; // max = holds the current largest number in the array.
         int index = 0; // index = stores the location of the second occurence of the largest number if it appears multiple times or the first occurence of the second largest if multiple appearances.
         int counter = 1; // keeps track and garuntees second occurence print of largest number in array if it appears multiple times.
      	
         if (inputNumbers == null) {
            return -1;
         }
      	// Part 1 -- Return the second occurence of the largest number if it appears multiple times in the array
         for (int i = 0; i <= inputNumbers.length - 1; i++) {
            if (inputNumbers[i] > max) { // search the array and find the maximum number and store it's index using a linear search
               max = inputNumbers[i];
               index = i;
               counter = 1; // everytime a new maximum is found, restore the counter to 1.
            }
            else if (inputNumbers[i] == max && counter != 2) {  //*PIVITAL STEP* 
               index = i; /* if another number is equal to the current maximum in the array and the count of */
               counter++; /* that max isn't equal to 2, store it's index and increment the counter. This garuntees the */
               			  /* array to continue to be searched in its entirity while not quitting early if a second */
               			  /* maximum is found when the maximum contiues to be reassigned as it goes through the array. */
            				  /* This also garuntees that if the largest number in the array occurs multiple times, the counter */
            				  /* will be 2 and the index of the second appearance will be stored and the loop will exit in the following */
            				  /* break statement at the end of the search for the max in the array, this will allow the second occurence */
            				  /* of the largest number in this random array to be returned if the largest number in the array occurs */
            				  /* multiple times. */
            } 
            else if (counter == 2 && i == inputNumbers.length - 1)
               break;
         } // end for
      	
      	// Part 2 -- Return the first occurence of the SECOND largest number in the array IF the LARGEST number in the array DOES NOT appear multiple times in the array.
         if (counter == 1) { // This if statement starts the search for the second largest because if the largest number in the array only appears once, the counter will remain 1 since there are no doubles in the array from the previous code in Part 1.
            int key = max - 1; // Search for the second largest by decrementing the max by 1
            boolean continueSearch = true; // boolean flag that exits while and for when the index of the second largest (key) is found.
         	
         	// the while loop is used to continue searching the array multiple times decrementing the key each time until the second largest is found, the only purpose of the loop is to continue searching if (max - 1) is not in the array when searched the first time.	
            while (continueSearch) { // Search through the entire array using linear search if (key = max - 1) is in the array
            		
               for (int k = 0; k <= inputNumbers.length - 1 && continueSearch; k++) { // linear search algorithm
                  if (key == inputNumbers[k]) {
                     index = k;
                     continueSearch = !continueSearch; // if the key is found exit while loop
                  } // end if
               } // end for
            		
               key--; // if max - 1 is not found in the array because the value doesn't exist in the array, then decrement the key by 1 and re-search the array until the second largest is found.
            } // end while
         } // end if
      
         return index;
      
      } // end indexOfSecondLargestNumber
   
       public static int[] sortNumbers(int[] inputNumbers) { //***METHOD 3***
      
         int currentElement = 0; // currentElement = current element being looked at to be sorted in unsorted array.
         int k; // k = place in the array used to switch values into sub list.
      
         if (inputNumbers == null) {
            return null;
         }
      
         for (int i = 1; i < inputNumbers.length; i++) { // Use insertion sort algorithm (don't quite understand the inner workings.)
            currentElement = inputNumbers[i]; 
            for (k = i - 1; k >= 0 && inputNumbers[k] > currentElement; k--) {
               inputNumbers[k + 1] = inputNumbers[k];
            }
            inputNumbers[k + 1] = currentElement;
         }
      
         return inputNumbers;
      } // end sortNumbers
   
       public static int sumGrid(int[][] inputNumbers) { //***METHOD 4***
         
         int arraySum = 0; // arraySum = variable used to store the sum of the elements in 2D random array.
      	
         if (inputNumbers == null) {
            return -1;
         }
      	
         for (int row = 0; row < inputNumbers.length; row++) {
            for (int column = 0; column < inputNumbers[row].length; column++) {
               arraySum += inputNumbers[row][column]; // Start at each row and sum all the columns until the 2D array ends.
            }
         }
      	
         return arraySum;
      	
      }
   	
       public static void printArrayResults(int[] randomArray, int[] sortedArray, int result, int counter, int[][] randomArray2D) {
       
         String output = ""; // output = string that holds result output to be displayed in windows for each method.
       
         if (counter == 1 || counter == 2) { // counter in main method determines which appropriate result message, error message, and array for each method should be printed.
            if (result == -1)
               JOptionPane.showMessageDialog(null, "Error: The array is empty. Please make a new array that's full.", "ERROR", JOptionPane.ERROR_MESSAGE);
            else {
               for (int j = 0; j <= randomArray.length - 1; j++) { // if no error is in method 1 or 2 returned, print the array
                  output += j + ": " + randomArray[j] + "\n";      
               
                  if (j == randomArray.length - 1 && counter == 1) // print this message for method 1
                     JOptionPane.showMessageDialog(null, output + "The index of the first occurence of the largest number in the array is " + result + ".", "Result", JOptionPane.INFORMATION_MESSAGE);
                  if (j == randomArray.length - 1 && counter == 2) // print this message for method 2
                     JOptionPane.showMessageDialog(null, output + "The index of the second largest number in the array is " + result + ".", "Result", JOptionPane.INFORMATION_MESSAGE);
               
               } // end for      
            } // end else
         } // end if
         else if (counter == 3) { // for method 3 print the unsorted array first because once the random array is sorted in the method, the values of the array will be changed permanently.
            output = "The unsorted array:\n";
         
            for (int k = 0; k <= randomArray.length - 1; k++) {
               output += k + ": " + randomArray[k] + "\n";
            } // end for
         
            sortedArray = sortNumbers(randomArray); // call method 3 to sort the unsorted random array
         
            if (sortedArray == null) 
               JOptionPane.showMessageDialog(null, "Error: The array is empty, please make a new array that's full.", "ERROR", JOptionPane.ERROR_MESSAGE);
            else { // if no error print the sorted array in the below else block
               output += "The sorted array:\n";
            
               for (int i = 0; i <= sortedArray.length - 1; i++) {
                  output += i + ": " + sortedArray[i] + "\n";
               } // end for
            
               JOptionPane.showMessageDialog(null, output, "Result", JOptionPane.INFORMATION_MESSAGE);
            } // end else
         } // end else if
         else { 
            for (int i = 0; i < randomArray2D.length; i++) { // for method 4 print the 2D random array by appending it to the output string
               for (int j = 0; j < randomArray2D[i].length; j++) {
                  output += randomArray2D[i][j] + " ";
               } // end inner for
               output += "\n";
            } // end outer for
         
            JOptionPane.showMessageDialog(null, output + "\n" + "The sum of the array elements is " + result + ".", "Result", JOptionPane.INFORMATION_MESSAGE); // print the output and array sum in a window
         } // end else 
      } // end printArrayResults
   	 
   } // end class