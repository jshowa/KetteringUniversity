import java.util.*;

public class TestStringTokenizer {
	public static void main(String[] args) {
		
		Entry[] tempEntry = new Entry[5];
		
		tempEntry[0] = new Entry("Jack Daniels", "9387 Park Side Lane #89, Pleasonton CA 39480", "930-343-3434");
		tempEntry[1] = new Entry("Jack Daniels", "9888 Battle Brook Dr., Farmington CA 34444", "810-999-9999");
		tempEntry[2] = new Entry("John Smith", "1225 Knollwood Dr., Boston MA 43434", "789-343-3434");
		tempEntry[3] = new Entry("Rusty", "123 Crescent Rd., Flint MI 48506", "810-931-2976");
		tempEntry[4] = new Entry("Zach Ousnamer", "235 Rocker Street, Flint MO 66666", "1-800-765-9678");
		
		String[] test = new String[5];
		String[] result = inputExample.split(":");
		for (int i = 0; i <= result.length - 1; i++)
			System.out.println(result[i]);
		
		//StringTokenizer inputExample1 = new StringTokenizer(inputExample, ":");
		
		//while (inputExample1.hasMoreTokens()) 
			//System.out.println(inputExample1.nextToken());
			
	int[] keys = binSearch("Jack Daniels", tempEntry);
	
	for (int k = 0; k <= keys.length - 1; k++)
		System.out.print(keys[k] + " ");
		
	}
	
	public static Entry[] tempCopy(Entry[] temp) {
		
		Entry[] tempCopy = new Entry[temp.length];
		
		for (int i = 0; i <= temp.length - 1; i++) 
			tempCopy[i] = temp[i];
		
		return tempCopy;
	}
	
	public static int[] binSearch(String key, Entry[] temp) {

		int arrayDecrementer = 1;
		int low = 0;
		int high = temp.length - 1;
		int mid;
		int[] keyIndexes = new int[5]; // <-- array size dependant on Entry[] temp array size
		int i = 0;
		
		
		Entry[] tempCopy = tempCopy(temp); // <-- make array copy
		
		while(i <= tempCopy.length - arrayDecrementer) { // <-- use copy and search the number of times equivalent to number of entries in Entry array to find duplicate entries 
			
			mid = (high + low) / 2;
			
			if(key.compareTo(tempCopy[mid].getName()) < 0)
				high = mid - 1;
			else if(key.compareTo(tempCopy[mid].getName()) == 0) {
				keyIndexes[i] = mid;
				tempCopy[mid] = null; // <-- after position of key is stored erase object from copied entry array and look for the next 
				i++;
				arrayDecrementer++;
				high = temp.length - arrayDecrementer; // <-- reset high and low to original defaults as well.
				low = 0;
			}
			else
				low = mid + 1;
		}
		
		return keyIndexes;
	} 
		
		
		
} 
		