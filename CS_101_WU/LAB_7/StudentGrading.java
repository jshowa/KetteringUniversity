   import java.io.*;
   import java.util.*;

    public class StudentGrading {
       public static void main(String[] args) throws Exception {
         
         ArrayList<StudentGrade> studentGradeList = new ArrayList<StudentGrade>(); //studentGradeList = An ArrayList/Vector array that has no set length so it can store multiple student objects
         StudentGrade[] studentGradeArray; //studentGradeArray = array used to store the sorted ArrayList of student objects by average grade*/
      
         if (args.length == 0 || args.length < 2) { // throw error if the command line args array has to many args or not enough args.
            System.out.print("Command Line Interface Usage: java StudentGrading sourceFileName targetFileName");
            System.exit(0);
         }
      
      // 1. Read contents of file from command line
         File sourceFile = new File(args[0]);
      
         if (!sourceFile.exists()) { // check if file exists, if not safely exit program.
            System.out.print("The file " + args[0] + "does not exist.");
            System.exit(0);
         }
         else {
            Scanner readFile = new Scanner(sourceFile); // create scanner to read in file data
            while(readFile.hasNext()) { // while input exists, create the objects with the input data and store them in the ArrayList
               studentGradeList.add(new StudentGrade(readFile.next(), readFile.next(), readFile.nextDouble(), readFile.nextDouble(), readFile.nextDouble(), readFile.nextDouble()));
            }
            readFile.close(); // close file when done reading
         }
      	
      // 2. Sort the ArrayList of student objects in ascending order using bubble sort
         studentGradeArray = sortByAverageGrade(studentGradeList); // call bubble sort method. 
      
         if (studentGradeArray == null) // if a null pointer was returned, exit the program safely.
            System.exit(0);
      
      // 3. Mark each student and print results
         markAndPrintResults(studentGradeArray, args[1]);
      } // end main
   
       public static StudentGrade[] sortByAverageGrade(ArrayList studentGradeList) {
      
         boolean needNextPass = true; // boolean flag variable that stops bubble sort from passing again when the array is sorted.
         int counter = 0; // counter used to tell when the array is sorted
         StudentGrade[] studentGradeArray = new StudentGrade[studentGradeList.size()]; // holds sorted array of ArrayList size
      
         if (studentGradeList == null) { // if the arrayList is referenced to null, return null
            System.out.println("Please pass an array that is not null.");
            return null;
         } // end if
      
         for (int i = 0; i <= studentGradeArray.length - 1; i++) { // Transition the objects in the ArrayList to a StudentGrade object array for easier coding and readability. (Could have been done with ArrayList)
            studentGradeArray[i] = ((StudentGrade)studentGradeList.get(i));
         } // end for
      
         for (int j = 1; j < studentGradeArray.length && needNextPass; j++) {
            counter = 0; // contiually reset counter until only one sort is done
            for (int k = 0; k < studentGradeArray.length - j; k++) {
               if (studentGradeArray[k].computeAverageGrade() > studentGradeArray[k + 1].computeAverageGrade()) { // bubble sort algorithm that moves large average grades to the highest index of the array, sorts in ascending order
                  StudentGrade temp = studentGradeArray[k];
                  studentGradeArray[k] = studentGradeArray[k + 1];
                  studentGradeArray[k + 1] = temp;
                  counter++; // count each "bubble" or swap
               } // end if
            
               if (counter > 1) // if more than 1 sort was passed, another pass is needed, otherwise there is no pass required. Used to end sort if the array is already sorted and provides an early exit out of the algorithm loops
                  needNextPass = true;
               else
                  needNextPass = !needNextPass;
            // end if
            } // end for
         } // end for
      
         return studentGradeArray;
      
      } // end sortByAverageGrade
      
       public static void markAndPrintResults(StudentGrade[] resultArray, String args) throws Exception {
      
         char gradeMark; //gradeMark = stores the character value/letter grade based on each students average grade points.
      
         File targetFile = new File(args); // create targetFile for saving information. Name specified at command line.
      
         if (targetFile.exists()) { // check if file with same name as target file exists in same directory, if there is throw error.
            System.out.println("The file already exists, please enter a new target file name.");
            System.exit(0);
         }
      
         PrintWriter output = new PrintWriter(targetFile); // create a PrintWriter object and write to targetFile object
      
         for (int i = 0; i <= resultArray.length - 1; i++) {
            gradeMark = resultArray[i].markStudentGrade(resultArray[i].computeAverageGrade()); // compute appropriate grade marking
            output.printf(resultArray[i].getName() + " " + resultArray[i].getLastName() + " %.1f" + " %c" + "\n", resultArray[i].computeAverageGrade(), gradeMark); // print results in ascending order with average grade to one place after decimal
         }
      
         output.close(); // close and save file after writing
      }
   
   } // end class
