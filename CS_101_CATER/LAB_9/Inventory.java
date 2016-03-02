import java.util.Scanner;
import java.io.File;

public class Inventory {
	public static void main (String[] args) throws Exception {
		
	
		Vehicle[] vehicle = new Vehicle[100];
		
		Scanner input = new Scanner(new File(args[0]));
		
		echo(args[0]);
		
		vehicle = vArray(input);
		
		printVehicle(sortId(vehicle));
		
		printMake(sortMake(vehicle));
		
		printPedal(sortOwner(makePedalArray(vehicle)));
		
		printNumDoors(sortNumOfDoors(makeCarArray(vehicle)));
		
		
	
		
			
		
	}
	public static void echo(String file) throws Exception {
		Scanner input = new Scanner(new File(file));
		System.out.println("***ECHO OF FILE***");
		
		while(input.hasNext()) {
			System.out.println(input.nextLine());
		}
		System.out.println();
	}
	
	public static Vehicle[] vArray(Scanner input) {
		Vehicle[] vehicle = new Vehicle[100];
		int counter = 0;
		String var = new String();
		
		while(input.hasNext()) {
			var = input.next();
			  
			if("b".equals(var)) {
				vehicle[counter] = new Bicycle(input.next(), input.nextInt(), input.nextInt(), input.next(), input.nextInt());
				counter++;
			}
			else if("c".equals(var)) {
				vehicle[counter] = new Car(input.next(), input.nextInt(), input.nextInt(), input.next()+ " " + input.next(), input.nextInt());
				counter++;
			}
			else if("t".equals(var)) {
				vehicle[counter] = new Tricycle(input.next(), input.next(), input.nextInt(), input.next(), input.nextInt());
				counter++;
			}
			else if("m".equals(var)) {
				vehicle[counter] = new Motorcycle(input.next(), input.next(), input.nextInt(), input.next()+ " " + input.next(), input.nextInt());
				counter++;
			}
			else {
				System.out.println("you smell");
				break;
			}
		}
		
		return vehicle;
	}
	
	public static Vehicle[] sortId(Vehicle[] list) {
		for (int i = 1; i < list.length; i++) {
			Vehicle currentElement = list[i];
			int k;
			
			if (list[i] == null) {
				break;
			}
			
			for (k = i - 1; k >= 0 && list[k].getId() > currentElement.getId(); k--) {
				list[k + 1] = list[k];
			}
			list[k + 1] = currentElement;
		}
		return list;
	}
		
	public static Vehicle[] sortMake(Vehicle[] list) {
		for (int i = 1; i < list.length; i++) {
			Vehicle currentElement = list[i];
			int k;
			if (list[i] == null) {
				break;
			}
			for (k = i - 1; k >= 0 && list[k].getMake().charAt(0) > currentElement.getMake().charAt(0); k--) {
				list[k + 1] = list[k];
			}
			list[k + 1] = currentElement;
		}
		return list;
	}
	
	public static Pedal[] makePedalArray(Vehicle[] list) {
		Pedal[] pedal = new Pedal[100];
		int counter = 0;
		int counter2 = 0;
		
		while(list[counter] != null) {
			if ("Bicycle".equals(list[counter].getClass().getName()) || "Tricycle".equals(list[counter].getClass().getName())) {
				pedal[counter2] = (Pedal)list[counter];
				counter++;
				counter2++;
			}
			else {
				counter++;
			}
		}
		return pedal;
	}
	
	public static Pedal[] sortOwner(Pedal[] pedal) {
			for (int i = 1; i < pedal.length; i++) {
			Pedal currentElement = pedal[i];
			int k;
			if (pedal[i] == null) {
				break;
			}
			for (k = i - 1; k >= 0 && pedal[k].getOwner().charAt(0) > currentElement.getOwner().charAt(0); k--) {
				pedal[k + 1] = pedal[k];
			}
			pedal[k + 1] = currentElement;
		}
		return pedal;
	}
	
		public static Car[] makeCarArray(Vehicle[] list) {
		Car[] car = new Car[100];
		int counter = 0;
		int counter2 = 0;
		
		while(list[counter] != null) {
			if ("Car".equals(list[counter].getClass().getName())) {
				car[counter2] = (Car)list[counter];
				counter++;
				counter2++;
			}
			else {
				counter++;
			}
		}
		return car;
	}
		public static Car[] sortNumOfDoors(Car[] car) {
			for (int i = 1; i < car.length; i++) {
			Car currentElement = car[i];
			int k;
			if (car[i] == null) {
				break;
			}
			for (k = i - 1; k >= 0 && car[k].getNumOfDoors() > currentElement.getNumOfDoors(); k--) {
				car[k + 1] = car[k];
			}
			car[k + 1] = currentElement;
		}
		return car;
	}
	
	public static void printVehicle(Vehicle[] list) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] != null && list[i] instanceof Vehicle) {
				System.out.println(list[i]);
			}
		}
	}
	
	public static void printMake(Vehicle[] list) {
		for (int i = 0; i < list.length; i++) {
			System.out.println(list[i]);
		}
	}
	
	public static void printPedal(Pedal[] pedal) {
		for (int i = 0; i < pedal.length; i++) {
			System.out.println(pedal[i]);
		}
	}
	
	public static void printNumDoors(Car[] car) {
		for (int i = 0; i < car.length; i++) {
			System.out.println(car[i]);
		}
	}
}
		