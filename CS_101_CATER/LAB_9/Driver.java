import java.io.File;
import java.util.Scanner;

public class Driver {
  
  public static int i = 0;
	
  public static void main (String [] args) throws Exception {
	 Scanner inputFile1;
	 Scanner inputFile2;
	 Scanner inputFile3;
	 Scanner inputFile4;
	 Scanner inputFile5;
	 Scanner inputFile;
	 final int MAXARRAYSIZE = 100;
	 
	 printID();
	 
	 Vehicle[] vehicles = new Vehicle[MAXARRAYSIZE];
	 
	 inputFile1 = new Scanner(new File(args[0]));
	 
	 System.out.println("Validating for file entry... ");
	 
	 while(inputFile1.hasNext()) {
	   inputFile2 = new Scanner(inputFile1.nextLine());
		inputFile2.useDelimiter("\t");
		
		entryValidator(inputFile2);
	 }
	 
	 System.out.println("Database entry validation complete! ");
	 System.out.println();
	 System.out.println("Validating for input... ");
	 
	 inputFile3 = new Scanner(new File(args[0]));
	 
	 // Loop for validating input
	 while(inputFile3.hasNext()) {
	   inputFile4 = new Scanner(inputFile3.nextLine());
		inputFile4.useDelimiter("\t");
		
		inputValidator(inputFile4);
	 }
	 
	 System.out.println("Validation Complete!");
	 
	 System.out.println();
	 
	 inputFile5 = new Scanner(new File(args[0]));
	 
	 System.out.println("Echoed File: ");
	 
	 while(inputFile5.hasNext()) {
	   inputFile = new Scanner(inputFile5.nextLine());
	   inputFile.useDelimiter("\t");
	 
	   objectCreation(inputFile, vehicles);
	 }
	 
	 sortIDNum(vehicles);
	 
	 sortMotorMake(vehicles);
	 
	 sortOwner(vehicles);
	 
	 sortDoorNum(vehicles);
	 
	}
	
   public static void printID() {
	  System.out.println("Jacob S. Howarth \nCS-101-07L, Spring 2007 \nLab 8 -- Vehicle Database");
	  System.out.println();
	}
  
   public static void objectCreation(Scanner inputFile, Vehicle[] vehicles) {
     for(; i < vehicles.length; i++) {
       if(!inputFile.hasNext()){
		 break;
		 }
	    
		 char temp1 = inputFile.next().charAt(0);
		
		 switch (temp1) {
		   case 'm': { Motorcycle m1 = new Motorcycle(inputFile.next(), 
		                               inputFile.next(), 
												 Integer.parseInt(inputFile.next()), 
												 inputFile.next(),
												 Integer.parseInt(inputFile.next()));
			  vehicles[i] = m1;
			  System.out.println(m1);									   
			  break;
			  }
			case 'c': { Car c1 = new Car(inputFile.next(), 
		                               Integer.parseInt(inputFile.next()), 
												 Integer.parseInt(inputFile.next()), 
												 inputFile.next(), 
												 Integer.parseInt(inputFile.next()));
			  vehicles[i] = c1;
			  System.out.println(c1);
			  break;
			  }
			case 'b': { Bicycle b1 = new Bicycle(inputFile.next(), 
		                               Integer.parseInt(inputFile.next()), 
												 Integer.parseInt(inputFile.next()), 
												 inputFile.next(), 
												 Integer.parseInt(inputFile.next()));
			  vehicles[i] = b1;
			  System.out.println(b1);
			  break;
		     }
			case 't': { Tricycle t1 = new Tricycle(inputFile.next(), 
		                               inputFile.next(), 
												 Integer.parseInt(inputFile.next()), 
												 inputFile.next(), 
												 Integer.parseInt(inputFile.next()));
			  vehicles[i] = t1;
			  System.out.println(t1);
			  break;
			  }
			default: { System.out.println("Error in the file and in validation. Please check the file. ");
			  System.out.println("End Program.");
			  System.exit(0);
			  break;
			  } 
		 }
     }
  }
  
  public static void sortIDNum(Vehicle[] vehicles) {
    boolean needNextPass = true;  
	 
	 for (int i = 1; i < vehicles.length; i++) {
	   needNextPass = false;
	 // System.out.println("i =" + i);
	   for (int j = 0; j < vehicles.length - i; j++) {
		// System.out.println("j =" + j);
		  
		  if (vehicles[j] == null || vehicles[j + 1] == null){
		    break;
		  }
		  
		  if ((vehicles[j].compareToID(vehicles[j + 1])) > 0) {
			   Vehicle temp = ((Vehicle)vehicles[j]);
			   vehicles[j] = vehicles[j + 1];
			   vehicles[j + 1] = temp;
				
				needNextPass = true;
		  }
	   }
	}
	 System.out.println();
	 System.out.println("Vehicle Sort by ID Number: ");
	
	 for (int z = 0; z < vehicles.length; z++) {
	   if(vehicles[z] != null){
	     System.out.println(vehicles[z]);
	   }
	 }
  }
 
  public static void sortMotorMake(Vehicle[] vehicles) {
    boolean nextPass = true;  
	 
	 for (int i = 1; i < vehicles.length; i++) {
	   nextPass = false;
	 // System.out.println("i =" + i);
	   for (int j = 0; j < vehicles.length - i; j++) {
		// System.out.println("j =" + j);
		  if (vehicles[j] == null || vehicles[j + 1] == null){
		    break;
		  }
		  
		  if ((vehicles[j].compareToMake(vehicles[j + 1])) > 0) {
			   
				Vehicle temp1 = ((Vehicle)vehicles[j]);
			   vehicles[j] = vehicles[j + 1];
			   vehicles[j + 1] = temp1;
				
				nextPass = true;
		  }
	   }
	}
	 System.out.println();
	 System.out.println("Vehicle Sort by Make: ");
	
	 for (int r = 0; r < vehicles.length; r++) {
	   if(vehicles[r] != null && (vehicles[r] instanceof Car || vehicles[r] instanceof Motorcycle)){
	     System.out.println(vehicles[r]);
		}
	 }
  }
  
  public static void sortOwner(Vehicle[] vehicles) {
    final int PEDALMAXARRAY = 100; 
	 Pedal[] pedal = new Pedal[PEDALMAXARRAY];
	 Vehicle[] nullVehicles = new Vehicle[PEDALMAXARRAY];
	 boolean nextPass = true;
	 int position = 0;
	 
	 for(int x = 0; x < vehicles.length; x++) {
	     if(vehicles[x] instanceof Pedal) {
		    pedal[position] = ((Pedal)vehicles[x]);
			 position++;
		  }
		  if(vehicles[x] instanceof Motorized) {  
		    nullVehicles[x] = vehicles[x];
		  }
	 }  
	 
	 for(int k = 1; k < pedal.length; k++) {
	   nextPass = false;
	     
		  for(int m = 0; m < pedal.length - k; m++) {
		    
			 if(pedal[m] == null || pedal[m + 1] == null) {
			   break;
			 }
		  
		    if((pedal[m].compareToOwner(pedal[m+1])) > 0) {
			   
				Pedal temp1 = pedal[m];
			   pedal[m] = pedal[m + 1];
			   pedal[m + 1] = temp1;
				
				nextPass = true;
			 }
		  }
	  }
	  System.out.println();
	  System.out.println("Pedal Sort by Owner: ");
	  
	  for(int s = 0; s < pedal.length; s++) {
	    if(pedal[s] != null && (pedal[s] instanceof Bicycle || pedal[s] instanceof Tricycle)) {
		   System.out.println(pedal[s]);
		 }
	  }		  
  }
  
  public static void sortDoorNum(Vehicle[] vehicles) {
    final int CARMAXARRAY = 100; 
	 Car[] car = new Car[CARMAXARRAY];
	 Vehicle[] nullVehicles = new Vehicle[CARMAXARRAY];
	 boolean nextPass = true;
	 int position = 0;
	 
	 for(int x = 0; x < vehicles.length; x++) {
	     if(vehicles[x] instanceof Car) {
		    car[position] = ((Car)vehicles[x]);
			 position++;
		  }
		  if(vehicles[x] instanceof Motorcycle && vehicles[x] instanceof Pedal) {  
		    nullVehicles[x] = vehicles[x];
		  }
	 }  
	 
	 for(int d = 1; d < car.length; d++) {
	   nextPass = false;
	     
		  for(int t = 0; t < car.length - d; t++) {
		    
			 if(car[t] == null || car[t + 1] == null) {
			   break;
			 }
		  
		    if((car[t].compareToDoorNum(car[t+1])) > 0) {
			   
				Car temp1 = car[t];
			   car[t] = car[t + 1];
			   car[t + 1] = temp1;
				
				nextPass = true;
			 }
		  }
	  }
	  System.out.println();
	  System.out.println("Car Sort by Number of Doors: ");
	  
	  for(int p = 0; p < car.length; p++) {
	    if(car[p] != null) {
		   System.out.println(car[p]);
		 }
	  }
	  
	}
	
  public static void entryValidator(Scanner inputFile2) {
	  char motorcycle = 'm';
	  char car = 'c';
	  char bicycle = 'b';
	  char tricycle = 't';
	  char charChecker = inputFile2.next().charAt(0);
	  
	  if(charChecker != motorcycle && charChecker != tricycle && charChecker != bicycle && charChecker != car) {
	    System.out.println();
		 System.out.println("Database entry error! Check each entry in the file and make sure each entry has" + "\n" +
		                  "'t' for tricycle" + "\n" + "'c' for car" + "\n" + "'b' for bicycle" + "\n" + "and 'm' for motorcycle.");
	    System.out.println();  
		 System.out.print("End Program.");
		 System.exit(0);
	  }  
  }
  // Check here in this method
  public static void inputValidator(Scanner inputFile4) {
     char motorcycle = 'm';
	  char car = 'c';
	  char bicycle = 'b';
	  char tricycle = 't';
	  char charChecker = inputFile4.next().charAt(0);
	  final int INPUTCHECKMAXSIZE = 100;
	  String[] inputCheck = new String[INPUTCHECKMAXSIZE];  

	  if(charChecker == car) {
	    for(int a = 0; a < inputCheck.length; a++) {
		   if(inputCheck[a] == null && inputCheck[a + 1] == null && inputFile4.hasNext() == false) {
			  break;
			}
	    inputCheck[a] = inputFile4.next();
		 }
		 if(inputCheck[5] != null) {
		   System.out.println();
		   System.out.println("Input Error! A database entry for car has an extra field. Please check the file. ");
			System.out.println();
			System.out.print("End Program.");
			System.exit(0);
		 }
	  }
	  else if(charChecker == motorcycle) {
	    for(int b = 0; b < inputCheck.length; b++) {
		   if(inputCheck[b] == null && inputCheck[b + 1] == null && inputFile4.hasNext() == false) {
			  break;
			  }
		   inputCheck[b] = inputFile4.next();
		 }
		 if(inputCheck[5] != null) {
		   System.out.println();
		   System.out.println("Input Error! A database entry for motorcycle has an extra field. Please check the file. ");
			System.out.println();
			System.out.print("End Program.");
			System.exit(0);
		 }
	  }
	  else if(charChecker == bicycle) {
	    for(int c = 0; c < inputCheck.length; c++) {
		   if(inputCheck[c] == null && inputCheck[c + 1] == null && inputFile4.hasNext() == false) {
			  break;
			  }
		   inputCheck[c] = inputFile4.next();
		 }
		 if(inputCheck[5] != null) {
		   System.out.println();  
			System.out.println("Input Error! A database entry for bicycle has an extra field. Please check the file. ");
			System.out.println();
			System.out.print("End Program.");
			System.exit(0);
		 }
	  }
	  else if(charChecker == tricycle) {
	    for(int d = 0; d < inputCheck.length; d++) {
		   if(inputCheck[d] == null && inputCheck[d + 1] == null && inputFile4.hasNext() == false) {
			  break;
			  }
         inputCheck[d] = inputFile4.next();
		 }
		 if(inputCheck[5] != null) {
	      System.out.println();
			System.out.println("Input Error! A database entry for tricycle has an extra field. Please check the file. ");
			System.out.println();
			System.out.print("End Program.");
			System.exit(0);
		 }
	  }
  }
}

