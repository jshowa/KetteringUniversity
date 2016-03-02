   import java.util.*;
   import java.io.*;

    public class TestGeoObjects {
       public static void main(String[] args) throws Exception {
         ArrayList<GeometricObject> geoObjectList = new ArrayList<GeometricObject>();
			Object[] geoList;
      	
      if (args.length == 0) { // throw error if the command line args array has to many args or not enough args.
            System.out.print("Command Line Interface Usage: java TestGeoObjects sourceFileName");
            System.exit(0);
         }
      
      //1. Create File object and read in data
         File inputFile = new File(args[0]);
      
         if (!inputFile.exists()) { // check if file exists, if not safely exit program.
            System.out.print("The file " + args[0] + "does not exist.");
            System.exit(0);
         }
      
         readFile(geoObjectList, inputFile);
      
      //2. Sum the areas of the individual objects
      computeAreaGeoObjects(geoObjectList);
      
      //3. Sum the perimeters of the individual objects
      computePerimeterGeoObjects(geoObjectList);
		
		geoList = geoObjectList.toArray();
		
		sort(geoList);
		
      
      } // end main
   
       public static void readFile(ArrayList<GeometricObject> geoObjectList, File inputFile) throws Exception {
         Scanner reader = new Scanner(inputFile);
         int objectType = 0;
      
         while (reader.hasNext()) { // read from the file
            objectType = reader.nextInt();
            switch (objectType) { // use a switch statement to determine which object to create based off the file standards
               case 0: geoObjectList.add(new CircleJH(reader.nextDouble())); // if the scanner see's 0, create a circle object
                  break;
               case 1: geoObjectList.add(new TriangleJH(reader.nextDouble(), reader.nextDouble(), reader.nextDouble())); // if the scanner see's 1, create triangle object
                  break;
               case 2: geoObjectList.add(new RectangleJH(reader.nextDouble(), reader.nextDouble())); // if the scanner see's 2, create rectangle object
                  break;
            } // end switch
         } // end while
      
         reader.close(); // close the file
      	
         for (int i = 0; i < geoObjectList.size(); i++) // print each object in the array list for verification on area and perimeter sum
            System.out.println((geoObjectList.get(i)).toString());
      	    
      } // end readFile
   	
       public static void computeAreaGeoObjects(ArrayList<GeometricObject> geoObjectList) {
         double completeAreaSum = 0;
      
         for (int i = 0; i < geoObjectList.size(); i++) // sum the each objects area into completeSum from ArrayList
            completeAreaSum += ((GeometricObject)geoObjectList.get(i)).getArea();
      
         System.out.printf("\nThe sum of the areas of the geometric objects = %.2f\n", completeAreaSum); // print the complete sum of the individual areas to two decimal places.
      
      } // end computeAreaGeoObjects
      
   	public static void computePerimeterGeoObjects(ArrayList<GeometricObject> geoObjectList) {
   	double completePerimeterSum = 0;
   	
   	for (int i = 0; i < geoObjectList.size(); i++) // sum each objects perimeter into completePerimeterSum
   	completePerimeterSum += ((GeometricObject)geoObjectList.get(i)).getPerimeter();
   	
   	System.out.printf("The sum of the perimeters of the geometric objects = %.2f\n", completePerimeterSum); // print the complete sum of the perimeters of each geometric object to two decimal places.
   	
   	} // end computePerimeterGeoObjects
   	
   } // end class