    public class StudentGrade {
   
      private String name; //name = String for first students name read from file
      private String lastName; //lastName = String for students last name read from file
      private double grade1; //grade 1-4 = students grades in each individual course
      private double grade2;
      private double grade3;
      private double grade4;
   
       StudentGrade() { // Zero arg constructor
         this(null, null, 0, 0, 0, 0);
      } // end StudentGrade
   
       StudentGrade(String name, String lastName, double grade1, double grade2, double grade3, double grade4) { // 4 arg constructor that creates object based on file input
         this.name = name; // assigns name to current instance
         this.lastName = lastName; // assigns last name to current student instance
         this.grade1 = grade1; // assigns grades 1-4 to current student instance for each student instance
         this.grade2 = grade2;
         this.grade3 = grade3;
         this.grade4 = grade4;
      } // end StudentGrade
   
       public String getName() { // Accessor to return the name of a student object
         return name;
      } // end getFirstName
   
       public String getLastName() { // Accessor to return the last name of a student object
         return lastName;
      } // end getLastName
   
       public double getGrade1() { // Accessors for each students name (**NOT NEEDED--USED FOR TEST PURPOSES**)
         return grade1;
      } // end getGrade1
   
       public double getGrade2() {
         return grade2;
      } // end getGrade2
   
       public double getGrade3() {
         return grade3;
      } // end getGrade3
   
       public double getGrade4() {
         return grade4;
      } // end getGrade4
   
       public double computeAverageGrade() { // Method to compute the average of a student objects 4 individual grades
         return (grade1 + grade2 + grade3 + grade4) / 4;
      } // end computeAverageGrade
   
       public char markStudentGrade(double averageGrade) { // Method used to assign a letter grade to a student object based off that students average grade.
      
         if (averageGrade >= 90) // rules that assign grading
            return 'A';
         else if (averageGrade >= 80 && averageGrade < 90) 
            return 'B';
         else if (averageGrade >= 70 && averageGrade < 80) 
            return 'C';
         else
            return 'D';
      
      } // end markStudentGrade
   } // end class


