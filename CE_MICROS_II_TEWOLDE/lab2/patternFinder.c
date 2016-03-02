/* Name: Jacob Howarth
 * Class: CE420L - Microcomputers II
 * Term: Fall 2011
 * Professor: Dr. Tewolde
 * Description: This program finds the first instance of a substring 
 *				in a piece of text, returning the position in the text
 *				at which it was found.
 */
 
/*** PREPROCESSOR DIRECTIVES ***/
#include <p24fj128ga010.h>

/*** FUNCTION PROTOTYPES ***/
int strLength(const char[]);


int main () {

	const char text[] = "The quick brown fox jumped over the lazy dog."; // text to search
	const char look[] = "The";	// substring to look for in text[]
	int txtLen, lookLen;		// variables to store length of text[] and look[] 
	int found = 0, pos = -1, i, j; // found flag for detection of look[] in text[]
								   // pos to hold the position of look[] in text[]
								   // i and j used as loop counters.
	
	txtLen = strLength(text);		// get the length of the text to search		
	lookLen = strLength(look);		// get the length of the substring to look 
									// for in the text
	
	// stop outer loop when substring is found or until there aren't enough characters
	// left in the text to match the substring
	for (i = 0; (i < (txtLen - lookLen + 1)) && !found; i++) {
		found = 1; 
		for (j = 0; j < lookLen && found; j++) {
			if (text[i + j] == look[j]) {	// match for char in text and char in substring
				pos = i;					// store position in text
				found = 1;					// set flag to potentially found
			}
			else {							// if there isn't a match
				pos = -1;					// reset position and found flag
				found = 0;
			}
		}
	}
	
	while (1);				  
}

/* Function: strLength
 * Args: const char[] - immutable array of chars (string)
 * Returns: int - number of characters in the string
 *
 * Description: Counts the number of characters in the string
 *				str, stopping at the null char when complete. 
 */
int strLength(const char str[]) {
	
	int count = 0;
	const char *strPtr = str; // get pointer to beginning of str
	while (*strPtr++)		  // move pointer until null char is reached
		count++;			  // count each movement of the pointer = str length
	return count;

}
	